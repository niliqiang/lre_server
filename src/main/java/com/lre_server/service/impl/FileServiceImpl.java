package com.lre_server.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lre_server.common.config.FileUploadProperties;
import com.lre_server.common.mqtt.MqttMsgClient;
import com.lre_server.common.tools.DateUtil;
import com.lre_server.common.tools.JsonResult;
import com.lre_server.common.tools.ResponseCode;
import com.lre_server.common.websocket.WebSocketServer;
import com.lre_server.dao.FileInfoMapper;
import com.lre_server.dao.SessionInfoMapper;
import com.lre_server.dao.SysUserMapper;
import com.lre_server.dao.UserClientMapper;
import com.lre_server.entity.FileInfo;
import com.lre_server.entity.SessionInfo;
import com.lre_server.entity.StatsInfoEntity;
import com.lre_server.service.FileService;
import com.lre_server.service.UserService;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * @ClassName: FileServiceImpl
 * @Author: niliqiang
 * @Date: 2021/1/27
 * @Description: TODO
 */
@Service
public class FileServiceImpl implements FileService {
    @Autowired
    private FileInfoMapper fileInfoMapper;

    @Autowired
    private FileUploadProperties fileUploadProperties;

    @Autowired
    private UserService userService;

    @Autowired
    private SessionInfoMapper sessionInfoMapper;

    @Autowired
    private UserClientMapper userClientMapper;

    @Autowired
    private SysUserMapper sysUserMapper;

    private static final String SHELL_FILE = "run_lr.sh";
    private static final String SHELL_FILE_DIR = "/home/admin/users/niliqiang/kaldi_lre/v1";

    static String finishRecognizeStr = "{\"msgId\":30, \"sessionId\":\"\", \"state\":{\"desired\":{\"directive\":\"FinishRecognize\"}}, \"result\":\"\"}";

    static Logger logger= LoggerFactory.getLogger(FileServiceImpl.class);

    /**
     * 查询文件列表
     * @param fileInfo
     * @return
     */
    @Override
    public PageInfo<FileInfo> queryFileList(FileInfo fileInfo, HttpServletRequest request) {
        if (request.isUserInRole("ROLE_USER")) {
            String currentUserName = userService.getCurrentUserName();
            fileInfo.setUserId(userService.queryByUserName(currentUserName).getUserId());
        }
        PageHelper.startPage(fileInfo.getPage(), fileInfo.getLimit());
        List<FileInfo> fileInfoList = fileInfoMapper.selectFileList(fileInfo);
        PageInfo<FileInfo> pageFileInfo = new PageInfo<>(fileInfoList);
        return pageFileInfo;
    }

    /**
     * 浏览器上传文件
     * @param file
     * @return
     */
    @Override
    public JsonResult browserAddFile(MultipartFile file) {
        // 判断文件是否空
        if (file == null || file.getOriginalFilename() == null || "".equalsIgnoreCase(file.getOriginalFilename().trim())) {
            return JsonResult.fail("文件为空");
        }
        // 存储文件夹
        Date fileUploadTime = Timestamp.valueOf(DateUtil.formatNormalDateTimeString(new Date()));   // 忽略毫秒
        String createTime = DateUtil.formatNormalDateString(fileUploadTime);
        String newPath = fileUploadProperties.getPath() + createTime + File.separator;
        File uploadDirectory = new File(newPath);
        if (uploadDirectory.exists()) {
            if (!uploadDirectory.isDirectory()) {
                uploadDirectory.delete();
            }
        } else {
            uploadDirectory.mkdir();
        }
        String currentUserName = userService.getCurrentUserName();
        if (currentUserName != null) {
            try {
                String originalFilename = file.getOriginalFilename();
                String fileName = originalFilename.substring(0, originalFilename.lastIndexOf("."));
                String fileSuffix = originalFilename.substring(originalFilename.lastIndexOf("."));
                String newFileName = currentUserName + "_" + fileName + "_" + DateUtil.formatNormalTimeString(fileUploadTime).replace(":", "") + fileSuffix;
                String newFilePath = newPath + newFileName;
                // 创建保存文件对象
                File saveFile = new File(newFilePath);
                FileUtils.copyInputStreamToFile(file.getInputStream(), saveFile);
                // 调用语种识别脚本
                Byte lreResult = languageRecognize(createTime + File.separator + newFileName, null, null);
                // 保存文件记录
                FileInfo fileInfo = new FileInfo();
                Integer userId = userService.queryByUserName(currentUserName).getUserId();
                fileInfo.setUserId(userId);
                fileInfo.setFileName(newFileName);
                fileInfo.setLreResult(lreResult);
                fileInfo.setStatus((byte) 1);
                fileInfo.setCreateTime(fileUploadTime);
                fileInfoMapper.insert(fileInfo);
                WebSocketServer.sendToUser(currentUserName, createTime);
                return JsonResult.success("文件上传成功，请稍后...");
            } catch (Exception e) {
                return JsonResult.fail("文件上传失败，请重试");
            }
        }
        return JsonResult.getResult(ResponseCode.TOKEN_ERROR);
    }

    /**
     * 文件下载
     * @param response
     * @param fileId
     * @return
     */
    @Override
    public JsonResult downloadFile(HttpServletResponse response, Integer fileId){
        FileInfo fileInfo = fileInfoMapper.selectByPrimaryKey(fileId);
        String fileName = fileInfo.getFileName();
        String fileFullPath = fileUploadProperties.getPath() + DateUtil.formatNormalDateString(fileInfo.getCreateTime()) + File.separator + fileName;
        File packetFile = new File(fileFullPath);
        if (packetFile.exists()) {
            try {
                // 配置文件下载
                response.setHeader("content-type", "application/octet-stream");
                response.setContentType("application/octet-stream");
                // 下载文件能正常显示中文
                response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
                ServletOutputStream outputStream = response.getOutputStream();
                byte[] buff = FileUtils.readFileToByteArray(packetFile);
                outputStream.write(buff);
                return null;
            } catch (Exception e) {
                return JsonResult.fail("编码方式不支持");
            }
        } else {
            return JsonResult.fail("对应文件不存在");
        }
    }

    @Override
    public JsonResult deleteFile(List<Integer> fileIds) {
        for (Integer fileId : fileIds) {
            fileInfoMapper.deleteByPrimaryKey(fileId);
        }
        return JsonResult.success("文件删除成功");
    }

    @Override
    public Integer getFileNumber(Integer userId) {
        FileInfo fileInfo = new FileInfo();
        fileInfo.setUserId(userId);
        return fileInfoMapper.selectFileList(fileInfo).size();
    }

    @Override
    public List<StatsInfoEntity> getFileStatsInfoList(Integer userId) {
        return fileInfoMapper.selectFileStatsInfoList(userId);
    }

    /**
     * 终端上传音频数据
     * @param file
     * @param sessionId
     * @return
     */
    @Override
    public JsonResult clientAddFile(MultipartFile file, String sessionId, String topicDirective) {
        // 判断文件是否空
        if (file == null || file.getOriginalFilename() == null || "".equalsIgnoreCase(file.getOriginalFilename().trim())) {
            return JsonResult.fail("音频数据为空");
        }
        SessionInfo sessionInfo = sessionInfoMapper.selectByPrimaryKey(sessionId);
        if (sessionInfo != null && sessionInfo.getLreResult() == null) {  // 当前会话存在且未失效
            String clientId = sessionInfo.getClientId();
            String clientName = userClientMapper.selectByPrimaryKey(clientId).getClientName();
            Integer userId = userClientMapper.selectByPrimaryKey(clientId).getUserId();
            String userName = sysUserMapper.selectByPrimaryKey(userId).getUserName();
            // 存储文件夹
            Date fileUploadTime = Timestamp.valueOf(DateUtil.formatNormalDateTimeString(new Date()));   // 忽略毫秒
            String createTime = DateUtil.formatNormalDateString(fileUploadTime);
            String newPath = fileUploadProperties.getPath() + createTime + File.separator;
            File uploadDirectory = new File(newPath);
            if (uploadDirectory.exists()) {
                if (!uploadDirectory.isDirectory()) {
                    uploadDirectory.delete();
                }
            } else {
                uploadDirectory.mkdir();
            }
            try {
                String newFileName = userName + "_" + clientName + "_" + DateUtil.formatNormalTimeString(fileUploadTime).replace(":", "") + ".pcm";
                String newFilePath = newPath + newFileName;
                // 创建保存文件对象
                File saveFile = new File(newFilePath);
                FileUtils.copyInputStreamToFile(file.getInputStream(), saveFile);
                // 调用语种识别脚本
                Byte lreResult = languageRecognize(createTime + File.separator + newFileName, sessionId, topicDirective);
                // 保存文件记录
                FileInfo fileInfo = new FileInfo();
                fileInfo.setUserId(userId);
                fileInfo.setFileName(newFileName);
                fileInfo.setLreResult(lreResult);
                fileInfo.setStatus((byte) 1);
                fileInfo.setCreateTime(fileUploadTime);
                fileInfoMapper.insert(fileInfo);
                WebSocketServer.sendToUser(userName, createTime);
                return JsonResult.success("音频数据上传成功，请稍后...");
            } catch (Exception e) {
                return JsonResult.fail("音频数据上传失败，请重试");
            }
        }
        return JsonResult.fail("终端设备鉴权失败，请重新唤醒识别");
    }

    /**
     * 调用shell脚本进行语种识别
     * @param argShell
     * @return
     */
    public Byte languageRecognize(String argShell, String sessionId, String topicDirective) {
        ProcessBuilder pb = new ProcessBuilder("./" + SHELL_FILE, argShell);
        pb.directory(new File(SHELL_FILE_DIR));
        Process process = null;
        BufferedReader bufferIn  = null;
        BufferedReader bufferErr  = null;
        StringBuffer output = new StringBuffer();
        String lreResult = null;
        Byte res = null;
        try {
            process = pb.start();
            // 获取命令执行结果, 有两个结果: 正常的输出 和 错误的输出（PS: 子进程的输出就是主进程的输入）
            bufferIn  = new BufferedReader(new InputStreamReader(process.getInputStream(), "UTF-8"));
            bufferErr  = new BufferedReader(new InputStreamReader(process.getErrorStream(), "UTF-8"));
            // 读取输出
            String line = null;
            while ((line = bufferIn.readLine()) != null) {
                output.append(line).append('\n');
            }
            while ((line = bufferErr.readLine()) != null) {
                output.append(line).append('\n');
            }
            try {
                process.waitFor();
                lreResult = output.toString().split("\n")[0];
                switch (lreResult) {
                    case "zh-CN":
                        res = 1;
                        break;
                    case "en":
                        res = 2;
                        break;
                    case "ru":
                        res = 3;
                        break;
                    case "es":
                        res = 4;
                        break;
                    case "ar":
                        res = 5;
                        break;
                    default:
                        res = 0;
                        logger.info(output.toString());
                        break;
                }
                // 推送语种识别结果给终端
                if (sessionId != null) {
                    JSONObject jFinishRecognize = JSON.parseObject(finishRecognizeStr);
                    jFinishRecognize.put("sessionId", sessionId);
                    jFinishRecognize.put("result", lreResult);
                    MqttMsgClient.getInstance().publish(topicDirective, jFinishRecognize.toString(), 0);
                    logger.info("推送消息主题:" + topicDirective);
                }
            } catch (InterruptedException ie) {
                ie.printStackTrace();
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {
            if (process != null) {
                process.destroy();
            }
            if (bufferIn != null) {
                try {
                    bufferIn.close();
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
            }
        }
        return res;
    }
}
