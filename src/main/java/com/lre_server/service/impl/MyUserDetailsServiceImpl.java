package com.lre_server.service.impl;

import com.lre_server.dao.SysRoleMapper;
import com.lre_server.dao.SysUserMapper;
import com.lre_server.dao.SysUserRoleMapper;
import com.lre_server.entity.SysRole;
import com.lre_server.entity.SysUser;
import com.lre_server.entity.SysUserRole;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: MyUserDetailsServiceImpl
 * @Author: niliqiang
 * @Date: 2021/1/7
 * @Description: TODO
 */
@Service
public class MyUserDetailsServiceImpl implements UserDetailsService {
    @Resource
    private SysUserMapper sysUserMapper;
    @Resource
    private SysRoleMapper sysRoleMapper;
    @Resource
    private SysUserRoleMapper sysUserRoleMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser sysUser = sysUserMapper.selectByUserName(username);
        if (null == sysUser) {
            throw new UsernameNotFoundException(username);
        }
        Integer userId = sysUser.getUserId();
        SysUserRole userRole = sysUserRoleMapper.selectByUserId(userId);
        SysRole role = sysRoleMapper.selectByPrimaryKey(userRole.getRoleId());
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getRoleName()));
        return new User(sysUser.getUserName(), sysUser.getPassword(), authorities);
    }
}
