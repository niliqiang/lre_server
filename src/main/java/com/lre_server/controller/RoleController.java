package com.lre_server.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.lre_server.entity.SysRole;
import com.lre_server.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * @ClassName: RoleController
 * @Author: niliqiang
 * @Date: 2021/2/18
 * @Description: TODO
 */
@Controller
@RequestMapping("/role")
public class RoleController {
    @Autowired
    private RoleService roleService;

    @RequestMapping(value = "/query-list")
    @ResponseBody
    public String queryRoleList(SysRole sysRole) {
        PageInfo pageObj = roleService.queryRoleList(sysRole);
        List<Map<String, Object>> roleList=pageObj.getList();
        JSONObject jo=new JSONObject();
        jo.put("code", 0);
        jo.put("count", pageObj.getTotal());
        jo.put("data", roleList);
        return jo.toString();
    }
}
