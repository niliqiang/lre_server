package com.lre_server.controller;

import com.lre_server.dao.SysUserMapper;
import com.lre_server.entity.SysUser;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.Date;

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
    //特殊符号，不包括'_'、'@'和'.'
    public static final String REG_SYMBOL = ".*[~!#$%^&*()+|<>,?/:;'\\[\\]{}\"]+.*";

    /**
     * 用户注册时检查密码是否由大小写字母和数字组成且不少于6位
     * @param password
     * @return
     */
    public static boolean checkPasswordRule(String password) {
        // 密码为空或者长度小于6位则返回false
        if (password == null || password.length() < 6) return false;
        int matchCount = 0;
        if (password.matches(REG_NUMBER)) matchCount++;
        if (password.matches(REG_LOWERCASE)) matchCount++;
        if (password.matches(REG_UPPERCASE)) matchCount++;
        if (matchCount < 3 || password.matches(REG_SYMBOL)) return false;
        return true;
    }

    /**
     * 用户注册时检查用户名是否不由特殊字符组成且不少于4位
     * @param userName
     * @return
     */
    public static boolean checkUserNameRule(String userName) {
        // 密码为空或者长度小于4位则返回false
        if (userName == null || userName.length() < 4) return false;
        if (userName.matches(REG_SYMBOL)) return false;
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
        // 判断两次输入的密码是否均不为空
        if (passwordArray.length < 2) {
            model.addAttribute("nullPasswordError", true);
            return "register";
        }
        // 判断两次输入的密码是否一致
        if (!passwordArray[0].equals(passwordArray[1])) {
            model.addAttribute("mismatchPasswordError", true);
            return "register";
        }
        // 判断输入的密码是否合法
        if (passwordArray.length > 2 || !checkPasswordRule(passwordArray[0])) {
            model.addAttribute("illegalPasswordError", true);
            return "register";
        }
        // 判断用户名是否为空
        if (sysUser.getUserName() == null || sysUser.getUserName() == "") {
            model.addAttribute("nullUserNameError", true);
            return "register";
        }
        // 判断用户名是否重复
        SysUser sysUserQuery = sysUserMapper.selectByUserName(sysUser.getUserName());
        if (sysUserQuery != null) {
            model.addAttribute("existedUserNameError", true);
            return "register";
        }
        // 判断用户名是否合法
        if (!checkUserNameRule(sysUser.getUserName())) {
            model.addAttribute("illegalUserNameError", true);
            return "register";
        }
        try {
            // 密码加密存储
            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
            String password = bCryptPasswordEncoder.encode(sysUser.getPassword());
            sysUser.setPassword(password);
            sysUser.setStatus((byte)0);
            sysUser.setCreateTime(new Date());
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
