package com.lre_server.service;

import com.github.pagehelper.PageInfo;
import com.lre_server.common.tools.JsonResult;
import com.lre_server.entity.FileInfo;
import com.lre_server.entity.StatsInfoEntity;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @InterfaceName: FileService
 * @Author: niliqiang
 * @Date: 2021/1/27
 * @Description: TODO
 */
public interface FileService {
    /**
     * 查询文件列表
     * @param fileInfo
     * @return
     */
    PageInfo<FileInfo> queryFileList(FileInfo fileInfo, HttpServletRequest request);

    /**
     * 浏览器上传音频文件
     * @param file
     * @return
     */
    JsonResult browserAddFile(MultipartFile file);

    /**
     * 前端下载缓存文件
     * @param response
     * @param fileId
     * @return
     */
    JsonResult downloadFile(HttpServletResponse response, Integer fileId);

    /**
     * 根据文件ID删除对应文件
     * @param fileIds
     * @return
     */
    JsonResult deleteFile(List<Integer> fileIds);

    /**
     * 获取当前用户的文件记录数，也就是语种识别次数
     * userId为null时，获取所有用户的文件记录数
     * @param userId
     * @return
     */
    Integer getFileNumber(Integer userId);

    /**
     * 获取当前用户的语种识别统计情况
     * @return
     */
    List<StatsInfoEntity> getFileStatsInfoList(Integer userId);

    /**
     * 终端上传音频文件
     * @param file
     * @param sessionId
     * @return
     */
    JsonResult clientAddFile(MultipartFile file, String sessionId);
}
