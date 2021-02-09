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

    @RequestMapping(value = "/queryList")
    @ResponseBody
    public String queryUserList(@RequestParam("page") Integer pageNum, @RequestParam("limit") Integer pageSize) {
        PageInfo pageObj = userService.queryUserList(pageNum, pageSize);
        List<Map<String, Object>> userList=pageObj.getList();
        JSONObject jo=new JSONObject();
        jo.put("code", 0);
        jo.put("count", pageObj.getTotal());
        jo.put("data", userList);
        return jo.toString();
    }

    @RequestMapping(value = "/info/{userName}")
    @ResponseBody
    public JsonResult queryUserInfo(@PathVariable("userName") String userName) {
        return JsonResult.success(userService.queryByUserName(userName));
    }

    @RequestMapping(value = "/info/update", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult updateUserInfo(@RequestBody SysUser sysUser) {
        return userService.updateByUserName(sysUser);
    }

}
