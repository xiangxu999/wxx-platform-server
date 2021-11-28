package com.xu.wxxplatformserver.service.impl;

import cn.hutool.core.map.MapUtil;
import com.xu.wxxplatformserver.common.Result;
import com.xu.wxxplatformserver.pojo.SysMenu;
import com.xu.wxxplatformserver.pojo.SysRole;
import com.xu.wxxplatformserver.pojo.SysUser;
import com.xu.wxxplatformserver.security.MyUserDetails;
import com.xu.wxxplatformserver.security.MyUserDetailsServiceImpl;
import com.xu.wxxplatformserver.service.UserService;
import com.xu.wxxplatformserver.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Description 登录、登出、注册接口实现
 * Date 2021/11/10 16:32
 * Version 1.0.1
 *
 * @author Wen
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private MyUserDetailsServiceImpl userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private SysUserServiceImpl sysUserService;

    @Autowired
    private SysMenuServiceImpl sysMenuService;

    @Autowired
    private SysRoleServiceImpl sysRoleService;

    @Override
    public SysUser register(SysUser sysUser) {
        return null;
    }

    @Override
    public String login(String username, String password) {
        String token = null;
        try {
            MyUserDetails userDetails = (MyUserDetails) userDetailsService.loadUserByUsername(username);
            if (!passwordEncoder.matches(password, userDetails.getPassword())) {
                throw new BadCredentialsException("密码不正确");
            }
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            token = jwtUtil.generateToken(userDetails);
        } catch (AuthenticationException e) {
            log.warn("登录异常:{}", e.getMessage());
        }
        return token;
    }

    @Override
    public Result logout() {
        MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        // 清除权限缓存
        sysUserService.clearUserAuthorityInfo(userDetails.getUsername());
        // 清除Security中的权限
        SecurityContextHolder.getContext().setAuthentication(null);
        return Result.success("退出成功");
    }

    @Override
    public Result userInfo(String token) {
        String username = jwtUtil.getUserNameFromToken(token);
        SysUser sysUser = sysUserService.getByUsername(username);

        // 得到当前登录用户的角色,用户前端生成路由，这里的角色是不带Role_前缀的
        String role = "";
        List<SysRole> sysRoleList = sysRoleService.getSysRoleListByUserId(sysUser.getUserId());
        if (sysRoleList.size() > 0) {
            role = sysRoleList.stream().map(SysRole::getCode).collect(Collectors.joining(","));
        }

        // 获取对应的菜单用户前端生成路由
        List<SysMenu> nav = sysMenuService.getCurrentUserNav(sysUser);

        return Result.success(MapUtil.builder()
                .put("id",sysUser.getUserId())
                .put("username", sysUser.getUsername())
                .put("avatar", sysUser.getAvatar())
                .put("created", sysUser.getCreated())
                .put("role",role)
                .put("nav",nav)
                .map());
    }
}
