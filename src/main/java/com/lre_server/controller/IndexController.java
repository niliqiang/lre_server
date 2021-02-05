package com.lre_server.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @ClassName: IndexController
 * @Author: niliqiang
 * @Date: 2021/1/26
 * @Description: TODO
 */
@Controller
@RequestMapping("/index")
public class IndexController {
    @GetMapping("/home")
    public String indexHome() {
        return "home";
    }

    @GetMapping("/login")
    public String indexLogin() {
        return "login";
    }

    @GetMapping("/user/info")
    public String userInfo(Model model) {
        model.addAttribute("flagType", "edit");
        return "user/user_info";
    }

    @GetMapping("/user")
    public String userList() {
        return "user/user_list";
    }

    @GetMapping("/file")
    public String fileList() {
        return "file/file_list";
    }

    @GetMapping("/client/add")
    public String addClient() {
        return "client/client_add";
    }

    @GetMapping("/client")
    public String clientList() {
        return "client/client_list";
    }

    @GetMapping("/session")
    public String sessionList() {
        return "session/session_list";
    }

}
