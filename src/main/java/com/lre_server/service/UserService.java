package com.lre_server.service;

import com.github.pagehelper.PageInfo;
import com.lre_server.common.tools.JsonResult;
import com.lre_server.entity.SysUser;

/**
 * @InterfaceName: UserService
 * @Author: niliqiang
 * @Date: 2021/1/29
 * @Description: TODO
 */
public interface UserService {
    PageInfo<SysUser> queryUserList(int page, int size);
    SysUser queryByUserName(String userName);
    JsonResult updateByUserName(SysUser sysUser);
}
