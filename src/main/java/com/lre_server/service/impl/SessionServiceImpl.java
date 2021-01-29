package com.lre_server.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lre_server.dao.SessionInfoMapper;
import com.lre_server.entity.SessionInfo;
import com.lre_server.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName: SessionServiceImpl
 * @Author: niliqiang
 * @Date: 2021/1/29
 * @Description: TODO
 */
@Service
public class SessionServiceImpl implements SessionService {
    @Autowired
    private SessionInfoMapper sessionInfoMapper;

    @Override
    public PageInfo<SessionInfo> querySessionList(int page, int size) {
        PageHelper.startPage(page, size);
        List<SessionInfo> sessionInfoList = sessionInfoMapper.sessionInfoList();
        PageInfo<SessionInfo> pageSessionInfo = new PageInfo<>(sessionInfoList);
        return pageSessionInfo;
    }
}
