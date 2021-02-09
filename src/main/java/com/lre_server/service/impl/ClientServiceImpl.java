package com.lre_server.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lre_server.common.tools.JsonResult;
import com.lre_server.common.tools.ResponseCode;
import com.lre_server.dao.UserClientMapper;
import com.lre_server.entity.UserClient;
import com.lre_server.service.ClientService;
import com.lre_server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @ClassName: ClientServiceImpl
 * @Author: niliqiang
 * @Date: 2021/1/29
 * @Description: TODO
 */
@Service
public class ClientServiceImpl implements ClientService {
    @Autowired
    private UserClientMapper userClientMapper;
    @Autowired
    private UserService userService;

    @Override
    public PageInfo<UserClient> queryClientList(UserClient userClient) {
        PageHelper.startPage(userClient.getPage(), userClient.getLimit());
        // 获取当前登录的用户信息
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUserName = authentication.getName();
            userClient.setUserId(userService.queryByUserName(currentUserName).getUserId());
        }
        List<UserClient> userClientList = userClientMapper.selectUserClientList(userClient);
        PageInfo<UserClient> pageClientInfo = new PageInfo<>(userClientList);
        return pageClientInfo;
    }

    @Override
    public JsonResult addClient(UserClient userClient) {
        UserClient existUserClient = userClientMapper.selectByClientName(userClient.getClientName());
        if (existUserClient != null) return JsonResult.fail("设备名称已存在，请更换试试吧");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUserName = authentication.getName();
            userClient.setUserId(userService.queryByUserName(currentUserName).getUserId());
            userClient.setStatus((byte)0);
            userClient.setCreateTime(new Date());
            userClientMapper.insert(userClient);
            return JsonResult.success();
        }
        return JsonResult.getResult(ResponseCode.TOKEN_ERROR);
    }
}
