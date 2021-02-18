package com.lre_server.service;

import com.github.pagehelper.PageInfo;
import com.lre_server.entity.SysRole;

/**
 * @InterfaceName: RoleService
 * @Author: niliqiang
 * @Date: 2021/2/18
 * @Description: TODO
 */
public interface RoleService {
    /**
     * 查询角色列表
     * @param sysRole
     * @return
     */
    PageInfo<SysRole> queryRoleList(SysRole sysRole);
}
