package com.xu.wxxplatformserver.security;

import com.xu.wxxplatformserver.pojo.SysUser;
import com.xu.wxxplatformserver.service.impl.SysUserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Description 自定义登录逻辑
 * Date 2021/11/9 17:26
 * Version 1.0.1
 *
 * @author Wen
 */
@Service
public class MyUserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    SysUserServiceImpl sysUserService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser sysUser = sysUserService.getByUsername(username);
        if (sysUser != null) {
            // 得到当前用户的权限信息
            String userAuthorityInfo = sysUserService.getUserAuthorityInfo(sysUser.getUserId());
            return new MyUserDetails(sysUser,userAuthorityInfo);
        } else {
            throw new UsernameNotFoundException("用户名或密码错误");
        }

    }
}
