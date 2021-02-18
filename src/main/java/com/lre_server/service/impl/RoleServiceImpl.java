package com.lre_server.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lre_server.dao.SysRoleMapper;
import com.lre_server.entity.SysRole;
import com.lre_server.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName: RoleServiceImpl
 * @Author: niliqiang
 * @Date: 2021/2/18
 * @Description: TODO
 */
@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Override
    public PageInfo<SysRole> queryRoleList(SysRole sysRole) {
        PageHelper.startPage(sysRole.getPage(), sysRole.getLimit());
        List<SysRole> sysRoleList = sysRoleMapper.selectRoleList(sysRole);
        PageInfo<SysRole> pageRoleInfo = new PageInfo<>(sysRoleList);
        return pageRoleInfo;
    }
}
