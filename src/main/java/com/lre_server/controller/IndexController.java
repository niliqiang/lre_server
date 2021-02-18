package com.lre_server.controller;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
    @GetMapping("/home")
    public String indexHome() {
        return "home";
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
    public String fileList() {
        return "file/file_list";
    }

    @GetMapping("/client/add")
    public String addClient(Model model) {
        model.addAttribute("clientUUID", UUID.randomUUID().toString().replace("-", ""));
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
