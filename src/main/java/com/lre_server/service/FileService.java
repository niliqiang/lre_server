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

    JsonResult browserAddFile(MultipartFile file);

    JsonResult downloadFile(HttpServletResponse response, Integer fileId);

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
}
