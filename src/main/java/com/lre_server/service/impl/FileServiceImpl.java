package com.lre_server.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lre_server.common.config.FileUploadProperties;
import com.lre_server.common.tools.DateUtil;
import com.lre_server.common.tools.JsonResult;
import com.lre_server.common.tools.ResponseCode;
import com.lre_server.common.websocket.WebSocketServer;
import com.lre_server.dao.FileInfoMapper;
import com.lre_server.entity.FileInfo;
import com.lre_server.entity.StatsInfoEntity;
import com.lre_server.service.FileService;
import com.lre_server.service.UserService;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
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
        //判断文件是否空
        if (file == null || file.getOriginalFilename() == null || "".equalsIgnoreCase(file.getOriginalFilename().trim())) {
            return JsonResult.fail("文件为空");
        }
        //存储文件夹
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
                //创建保存文件对象
                File saveFile = new File(newFilePath);
                FileUtils.copyInputStreamToFile(file.getInputStream(), saveFile);
                //保存文件记录
                FileInfo fileInfo = new FileInfo();
                fileInfo.setUserId(userService.queryByUserName(currentUserName).getUserId());
                fileInfo.setFileName(newFileName);
                fileInfo.setLreResult((byte) -1);
                fileInfo.setStatus((byte) 1);
                fileInfo.setCreateTime(fileUploadTime);
                fileInfoMapper.insert(fileInfo);
                WebSocketServer.sendInfo(createTime);
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
}
