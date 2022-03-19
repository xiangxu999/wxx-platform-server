package me.xu.service.impl;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import me.xu.common.Result;
import me.xu.pojo.SysMenu;
import me.xu.pojo.SysRole;
import me.xu.pojo.SysUser;
import me.xu.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
    public SaTokenInfo login(String username, String password) {
        SysUser checkUser = sysUserService.getByUsername(username);
        if (checkUser == null) {
            return null;
        }
        if (StrUtil.equals(checkUser.getPassword(), password)) {
            StpUtil.login(checkUser.getUserId());
            // 记录登录的时间（无论成功或者失败）
            checkUser.setLastLogin(LocalDateTime.parse(DateUtil.now(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            sysUserService.updateById(checkUser);
            // 把当前用户存入session
            StpUtil.getSession().set("userInfo",checkUser);
            return StpUtil.getTokenInfo();
        } else {
            return null;
        }

    }

    @Override
    public Result logout() {
        if (StpUtil.isLogin()) {
            StpUtil.logout();
            return Result.success("退出成功");
        } else {
            return Result.failed("退出失败");
        }
    }

    @Override
    public Result userInfo(String token) {
        Object loginIdByToken = StpUtil.getLoginIdByToken(token);
        SysUser sysUser = sysUserService.getByUserId(Long.valueOf(String.valueOf(loginIdByToken)));
        // 得到当前登录用户的角色,用户前端生成路由
        String role = "";
        List<SysRole> sysRoleList = sysRoleService.getSysRoleListByUserId(sysUser.getUserId());
        if (sysRoleList.size() > 0) {
            role = sysRoleList.stream().map(SysRole::getCode).collect(Collectors.joining(","));
        }
        // 获取对应的菜单用户前端生成路由
        List<SysMenu> nav = sysMenuService.getCurrentUserNav(sysUser);
        return Result.success(MapUtil.builder()
                .put("id", sysUser.getUserId())
                .put("username", sysUser.getUsername())
                .put("avatar", sysUser.getAvatar())
                .put("created", sysUser.getCreated())
                .put("role", role)
                .put("nav", nav)
                .map());
    }
}
