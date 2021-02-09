package com.lre_server.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.lre_server.common.tools.JsonResult;
import com.lre_server.entity.UserClient;
import com.lre_server.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @ClassName: ClientController
 * @Author: niliqiang
 * @Date: 2021/1/29
 * @Description: TODO
 */
@Controller
@RequestMapping("/client")
public class ClientController {
    @Autowired
    private ClientService clientService;

    @RequestMapping(value = "/queryList")
    @ResponseBody
    public String queryClientList(UserClient userClient) {
        PageInfo pageObj = clientService.queryClientList(userClient);
        List<Map<String, Object>> clientList=pageObj.getList();
        JSONObject jo=new JSONObject();
        jo.put("code", 0);
        jo.put("count", pageObj.getTotal());
        jo.put("data", clientList);
        return jo.toString();
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult addUserClient(@RequestBody UserClient userClient) {
        return clientService.addClient(userClient);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    @ResponseBody
    public JsonResult deleteUserClient(@RequestBody List<String> userClientIds) {
        return clientService.deleteClient(userClientIds);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult updateUserClient(@RequestBody UserClient userClient) {
        return clientService.updateClient(userClient);
    }
}
