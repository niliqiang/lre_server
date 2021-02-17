package com.lre_server.service;

import com.github.pagehelper.PageInfo;
import com.lre_server.common.tools.JsonResult;
import com.lre_server.entity.FileInfo;
import org.springframework.web.multipart.MultipartFile;

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
    PageInfo<FileInfo> queryFileList(FileInfo fileInfo);

    JsonResult browserAddFile(MultipartFile file);

    JsonResult downloadFile(HttpServletResponse response, String fileName);

    JsonResult deleteFile(List<Integer> fileIds);
}
