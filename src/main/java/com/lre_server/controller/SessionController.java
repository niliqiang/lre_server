package com.lre_server.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.lre_server.common.tools.JsonResult;
import com.lre_server.entity.SessionInfo;
import com.lre_server.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: SessionController
 * @Author: niliqiang
 * @Date: 2021/1/29
 * @Description: TODO
 */
@Controller
@RequestMapping("/session")
public class SessionController {
    @Autowired
    private SessionService sessionService;

    @RequestMapping(value = "/query-list")
    @ResponseBody
    public String queryList(SessionInfo sessionInfo, HttpServletRequest request) {
        PageInfo pageObj = sessionService.querySessionList(sessionInfo, request);
        List<Map<String, Object>> sessionList=pageObj.getList();
        JSONObject jo=new JSONObject();
        jo.put("code", 0);
        jo.put("count", pageObj.getTotal());
        jo.put("data", sessionList);
        return jo.toString();
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    @ResponseBody
    public JsonResult deleteSession(@RequestBody List<String> sessionIds) {
        return sessionService.deleteSession(sessionIds);
    }

}
