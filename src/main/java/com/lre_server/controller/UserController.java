package com.lre_server.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.lre_server.common.tools.JsonResult;
import com.lre_server.entity.SysUser;
import com.lre_server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @ClassName: UserController
 * @Author: niliqiang
 * @Date: 2021/1/29
 * @Description: TODO
 */
@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/query-list")
    @ResponseBody
    public String queryUserList(SysUser sysUser) {
        PageInfo pageObj = userService.queryUserList(sysUser);
        List<Map<String, Object>> userList=pageObj.getList();
        JSONObject jo=new JSONObject();
        jo.put("code", 0);
        jo.put("count", pageObj.getTotal());
        jo.put("data", userList);
        return jo.toString();
    }

    @RequestMapping(value = "/info/{user-name}")
    @ResponseBody
    public JsonResult queryUserInfo(@PathVariable("user-name") String userName) {
        return JsonResult.success(userService.queryByUserName(userName));
    }

    @RequestMapping(value = "/info/update", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult updateUserInfo(@RequestBody SysUser sysUser) {
        return userService.updateByUserName(sysUser);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    @ResponseBody
    public JsonResult deleteUser(@RequestBody List<Integer> sysUserIds) {
        return userService.deleteUser(sysUserIds);
    }

    @RequestMapping(value = "/status/update", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult updateUserStatus(@RequestBody SysUser sysUser) {
        return userService.updateUserStatus(sysUser);
    }

}
