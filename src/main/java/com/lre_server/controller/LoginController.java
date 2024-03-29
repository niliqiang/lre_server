package com.lre_server.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @ClassName: LoginController
 * @Author: niliqiang
 * @Date: 2021/1/6
 * @Description: TODO
 */
@Controller
public class LoginController {
    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @RequestMapping("/login-error")
    public String loginError(Model model) {
        // 登录错误
        model.addAttribute("error", true);
        return "login";
    }
}
