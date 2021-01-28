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

    @GetMapping("/user/info")
    public String userDetail(Model model) {
        model.addAttribute("flagType", "edit");
        return "user/user_edit";
    }

    @GetMapping("/user")
    public String userList() {
        return "user/user_list";
    }

    @GetMapping("/file")
    public String sysFiles() {
        return "file/file_list";
    }

}
