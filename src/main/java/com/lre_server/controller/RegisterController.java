package com.lre_server.controller;

import com.lre_server.dao.SysUserMapper;
import com.lre_server.entity.SysUser;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

/**
 * @ClassName: RegisterController
 * @Author: niliqiang
 * @Date: 2021/1/7
 * @Description: TODO
 */
@Controller
public class RegisterController {
    @Resource
    private SysUserMapper sysUserMapper;

    //数字
    public static final String REG_NUMBER = ".*\\d+.*";
    //小写字母
    public static final String REG_UPPERCASE = ".*[A-Z]+.*";
    //大写字母
    public static final String REG_LOWERCASE = ".*[a-z]+.*";

    public static boolean checkPasswordRule(String password) {
        //密码为空或者长度小于8位则返回false
        if (password == null || password.length() < 6) return false;
        int matchCount = 0;
        if (password.matches(REG_NUMBER)) matchCount++;
        if (password.matches(REG_LOWERCASE)) matchCount++;
        if (password.matches(REG_UPPERCASE)) matchCount++;
        if (matchCount < 3 )  return false;
        return true;
    }

    @RequestMapping("/register")
    public String register() {
        return "register";
    }

    @RequestMapping("/register-error")
    public String registerError(Model model) {
        // Model 的作用是往 Web 页面传数据
        // model 添加一个参数 error 其作用是如果此参数为 true，就显示下面一行 HTML 代码
        // <p th:if="${error}" class="error">注册错误</p>
        model.addAttribute("error", true);
        return "register";
    }

    @RequestMapping("/register-save")
    public String registerSave(@ModelAttribute SysUser sysUser, Model model) {
        String[] passwordArray = sysUser.getPassword().split(",");
        if (passwordArray.length < 2) {
            model.addAttribute("nullPasswordError", true);
            return "register";
        }
        if (!passwordArray[0].equals(passwordArray[1])) {
            model.addAttribute("mismatchError", true);
            return "register";
        }
        if (passwordArray.length > 2 || !checkPasswordRule(passwordArray[0])) {
            model.addAttribute("illegalError", true);
            return "register";
        }
        // 判断 userName 不能为空
        if (sysUser.getUserName() == null || sysUser.getUserName() == "") {
            model.addAttribute("nullUserNameError", true);
            return "register";
        }
        try {
            // 密码加密存储
            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
            String password = bCryptPasswordEncoder.encode(sysUser.getPassword());
            sysUser.setPassword(password);
            // 写入数据库
            sysUserMapper.insert(sysUser);
            //  重定向到 login 页面
            return "redirect:/login";
        } catch (Exception e) {
            // 注册错误
            model.addAttribute("error", true);
            return "register";
        }
    }


}
