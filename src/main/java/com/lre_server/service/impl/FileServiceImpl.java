package com.lre_server.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lre_server.dao.FileInfoMapper;
import com.lre_server.entity.FileInfo;
import com.lre_server.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    FileInfoMapper fileInfoMapper;

    @Override
    public PageInfo<FileInfo> queryFilesList(int page, int size) {
        PageHelper.startPage(page, size);
        List<FileInfo> fileInfoList = fileInfoMapper.fileInfoList();
        PageInfo<FileInfo> pageFileInfo = new PageInfo<FileInfo>(fileInfoList);
        return pageFileInfo;
    }
}
