package com.lre_server.service;

import com.github.pagehelper.PageInfo;
import com.lre_server.entity.UserClient;

/**
 * @InterfaceName: ClientService
 * @Author: niliqiang
 * @Date: 2021/1/29
 * @Description: TODO
 */
public interface ClientService {
    PageInfo<UserClient> queryClientList(int page, int size);
}
