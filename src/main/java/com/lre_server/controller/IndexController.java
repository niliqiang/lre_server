package com.lre_server.controller;

import com.alibaba.fastjson.JSONObject;
import com.lre_server.entity.FileInfo;
import com.lre_server.entity.StatsInfoEntity;
import com.lre_server.service.ClientService;
import com.lre_server.service.FileService;
import com.lre_server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @ClassName: IndexController
 * @Author: niliqiang
 * @Date: 2021/1/26
 * @Description: TODO
 */
@Controller
@RequestMapping("/index")
public class IndexController {
    @Autowired
    private UserService userService;
    @Autowired
    private ClientService clientService;
    @Autowired
    private FileService fileService;

    private void setUserStatusForHTML(Model model) {
        Byte userStatus = userService.queryByUserName(userService.getCurrentUserName()).getStatus();
        if (userStatus == 0) {
            model.addAttribute("userStatus", "disable");
        } else if (userStatus == -1){
            model.addAttribute("userStatus", "pending");
        } else {
            model.addAttribute("userStatus", "enable");
        }
    }

    @GetMapping("/home")
    public String indexHome(Model model) {
        model.addAttribute("userName", userService.getCurrentUserName());
        return "home";
    }

    @RequestMapping("/stats/info/{userName}")
    @ResponseBody
    public String getStatsInfo(@PathVariable("userName") String userName) {
        // 用户统计
        Integer userNumber = userService.getUserNumber();
        // 设备统计
        Integer currentUserId = userService.queryByUserName(userName).getUserId();
        Integer sysClientNumber = clientService.getClientNumber(null);
        Integer userClientNumber = clientService.getClientNumber(currentUserId);
        // 语种识别统计
        Integer sysFileNumber = fileService.getFileNumber(null);
        Integer userFileNumber = fileService.getFileNumber(currentUserId);
        HashMap<String, Object> statsInfoMap = new HashMap<>();
        statsInfoMap.put("userNumber", userNumber);
        statsInfoMap.put("sysClientNumber", sysClientNumber);
        statsInfoMap.put("userClientNumber", userClientNumber);
        statsInfoMap.put("sysFileNumber", sysFileNumber);
        statsInfoMap.put("userFileNumber", userFileNumber);
        JSONObject jo=new JSONObject();
        jo.put("code", 0);
        jo.put("data", statsInfoMap);
        return jo.toString();
    }

    @RequestMapping("/stats/fileInfo")
    @ResponseBody
    public List<StatsInfoEntity> getFileStatsInfoList(HttpServletRequest request) {
        // 管理员用户不区分userId
        Integer currentUserId = null;
        if (request.isUserInRole("ROLE_USER")) {
            currentUserId = userService.queryByUserName(userService.getCurrentUserName()).getUserId();
        }
        return fileService.getFileStatsInfoList(currentUserId);
    }

    @GetMapping("/login")
    public String indexLogin() {
        return "login";
    }

    @GetMapping("/error")
    public String indexError() {
        return "error";
    }

    @GetMapping("/user/info")
    public String userInfo(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUserName = authentication.getName();
            model.addAttribute("userName", currentUserName);
        }
        model.addAttribute("flagType", "edit");
        return "user/user_info";
    }

    @GetMapping("/role")
    public String roleList() {
        return "role/role_list";
    }

    @GetMapping("/user")
    public String userList() {
        return "user/user_list";
    }

    @GetMapping("/file")
    public String fileList(Model model) {
        setUserStatusForHTML(model);
        return "file/file_list";
    }

    @GetMapping("/client/add")
    public String addClient(Model model) {
        setUserStatusForHTML(model);
        model.addAttribute("clientUUID", UUID.randomUUID().toString().replace("-", ""));
        return "client/client_add";
    }

    @GetMapping("/client")
    public String clientList(Model model) {
        setUserStatusForHTML(model);
        return "client/client_list";
    }

    @GetMapping("/session")
    public String sessionList(Model model) {
        setUserStatusForHTML(model);
        return "session/session_list";
    }
}
