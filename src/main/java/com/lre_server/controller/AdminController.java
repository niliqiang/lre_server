package com.lre_server.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @ClassName: AdminController
 * @Author: niliqiang
 * @Date: 2021/1/7
 * @Description: TODO
 */
@Controller
public class AdminController {
    // 需要 ROLE_ADMIN 角色才行访问 /admin
    // 这也是为什么 MyUserDetailsServiceImpl 需要 "ROLE_" + sysUser.getUserRole()
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping("/admin")
    public String admin() {
        return "admin";
    }
}
