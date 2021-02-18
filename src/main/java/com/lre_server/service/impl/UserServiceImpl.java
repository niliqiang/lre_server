package com.lre_server.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lre_server.common.tools.JsonResult;
import com.lre_server.dao.SysUserMapper;
import com.lre_server.entity.SysUser;
import com.lre_server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
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
    private SysUserMapper sysUserMapper;

    @Override
    public PageInfo<SysUser> queryUserList(SysUser sysUser) {
        PageHelper.startPage(sysUser.getPage(), sysUser.getLimit());
        List<SysUser> sysUserList = sysUserMapper.selectUserList(sysUser);
        PageInfo<SysUser> pageUserInfo = new PageInfo<>(sysUserList);
        return pageUserInfo;
    }

    @Override
    public SysUser queryByUserName(String userName) {
        return sysUserMapper.selectByUserName(userName);
    }

    @Override
    public JsonResult updateByUserName(SysUser sysUser) {
        SysUser record = sysUserMapper.selectByUserName(sysUser.getUserName());
        sysUser.setUserId(record.getUserId());
        sysUser.setPassword(record.getPassword());
        sysUser.setCreateTime(record.getCreateTime());
        sysUser.setUpdateTime(new Date());
        sysUserMapper.updateByPrimaryKey(sysUser);
        return JsonResult.success("用户基本资料更新成功");
    }

    @Override
    public JsonResult deleteUser(List<Integer> sysUserIds) {
        for (Integer sysUserId : sysUserIds) {
            sysUserMapper.deleteByPrimaryKey(sysUserId);
        }
        return JsonResult.success("用户删除成功");
    }
}
