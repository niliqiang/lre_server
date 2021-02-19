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
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
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
    public PageInfo<UserClient> queryClientList(UserClient userClient, HttpServletRequest request) {
        PageHelper.startPage(userClient.getPage(), userClient.getLimit());
        if (request.isUserInRole("ROLE_USER")) {
            String currentUserName = userService.getCurrentUserName();
            userClient.setUserId(userService.queryByUserName(currentUserName).getUserId());
        }
        List<UserClient> userClientList = userClientMapper.selectClientList(userClient);
        PageInfo<UserClient> pageClientInfo = new PageInfo<>(userClientList);
        return pageClientInfo;
    }

    @Override
    public JsonResult addClient(UserClient userClient) {
        UserClient existUserClient = userClientMapper.selectByClientName(userClient.getClientName());
        if (existUserClient != null) return JsonResult.fail("设备名称已存在，请更换试试吧");
        String currentUserName = userService.getCurrentUserName();
        if (currentUserName != null) {
            userClient.setUserId(userService.queryByUserName(currentUserName).getUserId());
            userClient.setStatus((byte)0);
            userClient.setCreateTime(new Date());
            userClientMapper.insert(userClient);
            return JsonResult.success("设备信息提交成功");
        }
        return JsonResult.getResult(ResponseCode.TOKEN_ERROR);
    }

    @Override
    public JsonResult deleteClient(List<String> userClientIds) {
        for (String clientId : userClientIds) {
            userClientMapper.deleteByPrimaryKey(clientId);
        }
        return JsonResult.success("设备删除成功");
    }

    @Override
    public JsonResult updateClient(UserClient userClient) {
        userClientMapper.updateByPrimaryKey(userClient);
        return JsonResult.success("设备信息提交成功");
    }
}
