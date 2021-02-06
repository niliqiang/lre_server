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
    public String queryList(@RequestParam("page") Integer pageNum, @RequestParam("limit") Integer pageSize) {
        PageInfo pageObj = clientService.queryClientList(pageNum, pageSize);
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
        JsonResult jsonResult = clientService.addClient(userClient);
        return jsonResult;
    }
}
