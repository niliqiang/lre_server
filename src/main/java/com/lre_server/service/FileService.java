package com.lre_server.service;

import com.github.pagehelper.PageInfo;
import com.lre_server.entity.FileInfo;

/**
 * @InterfaceName: FileService
 * @Author: niliqiang
 * @Date: 2021/1/27
 * @Description: TODO
 */
public interface FileService {
    PageInfo<FileInfo> queryFilesList(int page, int size);
}
