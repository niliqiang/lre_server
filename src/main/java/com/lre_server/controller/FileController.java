package com.lre_server.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.lre_server.common.tools.ServletUtil;
import com.lre_server.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
//        int page = Integer.parseInt(request.getParameter("page")); // 取得当前页数,注意这是jqgrid自身的参数
//        int rows = Integer.parseInt(request.getParameter("rows")); // 取得每页显示行数，,注意这是jqgrid自身的参数
        PageInfo pageObj = fileService.queryFilesList(pageNum, pageSize);
        List<Map<String, Object>> fileList=pageObj.getList();
        JSONObject jo=new JSONObject();
//        jo.put("rows", fileList); //包含实际数据的数组
//        jo.put("total", pageObj.getPages()); //总页数
//        jo.put("records", pageObj.getTotal()); //查询出的记录数
//        ServletUtil.createSuccessResponse(200, jo, response);
        jo.put("code", 0);
        jo.put("count", pageObj.getTotal());
        jo.put("data", fileList);
        return jo.toString();
    }
}
