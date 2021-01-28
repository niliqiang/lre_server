package com.lre_server.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lre_server.dao.SysUserMapper;
import com.lre_server.entity.SysUser;
import com.lre_server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName: UserServiceImpl
 * @Author: niliqiang
 * @Date: 2021/1/29
 * @Description: TODO
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    SysUserMapper sysUserMapper;

    @Override
    public PageInfo<SysUser> queryUserList(int page, int size) {
        PageHelper.startPage(page, size);
        List<SysUser> sysUserList = sysUserMapper.sysUserList();
        PageInfo<SysUser> pageUserInfo = new PageInfo<SysUser>(sysUserList);
        return pageUserInfo;
    }

}
