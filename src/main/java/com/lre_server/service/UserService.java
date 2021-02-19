package com.lre_server.service;

import com.github.pagehelper.PageInfo;
import com.lre_server.common.tools.JsonResult;
import com.lre_server.entity.SysUser;

import java.util.List;

/**
 * @InterfaceName: UserService
 * @Author: niliqiang
 * @Date: 2021/1/29
 * @Description: TODO
 */
public interface UserService {
    /**
     * 获取当前登录用户的用户名
     * @return
     */
    String getCurrentUserName();
    /**
     * 查询用户列表
     * @param sysUser
     * @return
     */
    PageInfo<SysUser> queryUserList(SysUser sysUser);

    /**
     * 更具用户名查询用户
     * @param userName
     * @return
     */
    SysUser queryByUserName(String userName);

    /**
     * 更新用户信息
     * @param sysUser
     * @return
     */
    JsonResult updateByUserName(SysUser sysUser);

    /**
     * 删除用户
     * @param sysUserIds
     * @return
     */
    JsonResult deleteUser(List<Integer> sysUserIds);

    /**
     * 更新用户状态
     * @param sysUser
     * @return
     */
    JsonResult updateUserStatus(SysUser sysUser);
}
