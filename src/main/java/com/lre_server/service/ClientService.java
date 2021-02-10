package com.lre_server.service;

import com.github.pagehelper.PageInfo;
import com.lre_server.common.tools.JsonResult;
import com.lre_server.entity.UserClient;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @InterfaceName: ClientService
 * @Author: niliqiang
 * @Date: 2021/1/29
 * @Description: TODO
 */
public interface ClientService {
    /**
     * 查询设备列表
     * @param userClient
     * @return
     */
    PageInfo<UserClient> queryClientList(UserClient userClient);

    /**
     * 用户创建虚拟设备
     * @param userClient
     */
    JsonResult addClient(UserClient userClient);

    /**
     * 删除虚拟设备
     * @param userClientIds
     * @return
     */
    JsonResult deleteClient(List<String> userClientIds);

    /**
     * 更新虚拟设备
     * @param userClient
     * @return
     */
    JsonResult updateClient(UserClient userClient);
}
