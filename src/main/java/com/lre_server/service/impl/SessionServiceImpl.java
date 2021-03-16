package com.lre_server.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lre_server.common.tools.JsonResult;
import com.lre_server.dao.SessionInfoMapper;
import com.lre_server.dao.SysUserMapper;
import com.lre_server.dao.UserClientMapper;
import com.lre_server.entity.SessionInfo;
import com.lre_server.entity.UserClient;
import com.lre_server.service.SessionService;
import com.lre_server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.UUID;

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
    @Autowired
    private UserClientMapper userClientMapper;
    @Autowired
    private SysUserMapper sysUserMapper;

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

    @Override
    public JsonResult addSession(String clientId) {
        UserClient userClient = userClientMapper.selectByPrimaryKey(clientId);
        if (userClient != null) {
            // 向数据库中插入会话信息
            String sessionId = UUID.randomUUID().toString().replace("-", "");
            SessionInfo sessionInfo = new SessionInfo();
            sessionInfo.setSessionId(sessionId);
            sessionInfo.setClientId(clientId);
            sessionInfo.setCreateTime(new Date());
            sessionInfoMapper.insert(sessionInfo);
            // 获取用户名
            Integer userId = userClient.getUserId();
            String userName = sysUserMapper.selectByPrimaryKey(userId).getUserName();
            JSONObject jsonData = new JSONObject();
            jsonData.put("userName", userName);
            jsonData.put("sessionId", sessionId);
            return JsonResult.success(jsonData);
        }
        return JsonResult.fail("设备不存在");
    }
}
