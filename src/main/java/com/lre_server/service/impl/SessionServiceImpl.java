package com.lre_server.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lre_server.common.tools.JsonResult;
import com.lre_server.dao.SessionInfoMapper;
import com.lre_server.entity.SessionInfo;
import com.lre_server.service.SessionService;
import com.lre_server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    @Autowired
    private UserService userService;

    @Override
    public PageInfo<SessionInfo> querySessionList(SessionInfo sessionInfo) {
        PageHelper.startPage(sessionInfo.getPage(), sessionInfo.getLimit());
        // 获取当前登录的用户信息
        int userId = 0;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUserName = authentication.getName();
            userId = userService.queryByUserName(currentUserName).getUserId();
        }
        List<SessionInfo> sessionInfoList = sessionInfoMapper.selectSessionList(sessionInfo, userId);
        PageInfo<SessionInfo> pageSessionInfo = new PageInfo<>(sessionInfoList);
        return pageSessionInfo;
    }

    @Override
    public JsonResult deleteSession(List<String> sessionIds) {
        for (String sessionId : sessionIds) {
            sessionInfoMapper.deleteByPrimaryKey(sessionId);
        }
        return JsonResult.success("会话删除成功");
    }
}
