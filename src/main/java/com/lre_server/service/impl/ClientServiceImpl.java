package com.lre_server.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lre_server.dao.UserClientMapper;
import com.lre_server.entity.UserClient;
import com.lre_server.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Override
    public PageInfo<UserClient> queryClientList(int page, int size) {
        PageHelper.startPage(page, size);
        List<UserClient> userClientList = userClientMapper.userClientList();
        PageInfo<UserClient> pageClientInfo = new PageInfo<>(userClientList);
        return pageClientInfo;
    }
}
