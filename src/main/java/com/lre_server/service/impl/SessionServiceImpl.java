package com.lre_server.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lre_server.common.tools.JsonResult;
import com.lre_server.dao.SessionInfoMapper;
import com.lre_server.entity.SessionInfo;
import com.lre_server.service.SessionService;
import com.lre_server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
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
    public PageInfo<SessionInfo> querySessionList(SessionInfo sessionInfo, HttpServletRequest request) {
        Integer userId = null;
        if (request.isUserInRole("ROLE_USER")) {
            String currentUserName = userService.getCurrentUserName();
            userId = userService.queryByUserName(currentUserName).getUserId();
        }
        PageHelper.startPage(sessionInfo.getPage(), sessionInfo.getLimit());
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
