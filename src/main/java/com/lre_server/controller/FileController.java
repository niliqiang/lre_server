package com.lre_server.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.lre_server.common.tools.JsonResult;
import com.lre_server.entity.FileInfo;
import com.lre_server.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    public String queryList(FileInfo fileInfo) {
        PageInfo pageObj = fileService.queryFileList(fileInfo);
        List<Map<String, Object>> fileList=pageObj.getList();
        JSONObject jo=new JSONObject();
        jo.put("code", 0);
        jo.put("count", pageObj.getTotal());
        jo.put("data", fileList);
        return jo.toString();
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult browserAddFile(@RequestParam(value = "file") MultipartFile file) {
        return fileService.browserAddFile(file);
    }

    @RequestMapping(value = "/download/{fileName}")
    @ResponseBody
    public JsonResult downloadFile(HttpServletResponse response, @PathVariable("fileName") String fileName) {
        return fileService.downloadFile(response, fileName);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    @ResponseBody
    public JsonResult deleteFile(@RequestBody List<Integer> fileIds) {
        return fileService.deleteFile(fileIds);
    }
}
