package com.lre_server.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.lre_server.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * @ClassName: FilesController
 * @Author: niliqiang
 * @Date: 2021/1/27
 * @Description: TODO
 */
@Controller
@RequestMapping("/file")
public class FileController {
    @Autowired
    private FileService fileService;

    @RequestMapping(value = "/queryList")
    @ResponseBody
    public String queryList(@RequestParam("page") Integer pageNum, @RequestParam("limit") Integer pageSize) {
        PageInfo pageObj = fileService.queryFileList(pageNum, pageSize);
        List<Map<String, Object>> fileList=pageObj.getList();
        JSONObject jo=new JSONObject();
        jo.put("code", 0);
        jo.put("count", pageObj.getTotal());
        jo.put("data", fileList);
        return jo.toString();
    }
}
