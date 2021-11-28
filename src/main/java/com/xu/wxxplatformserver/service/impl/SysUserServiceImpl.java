package com.xu.wxxplatformserver.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xu.wxxplatformserver.consts.CommonConst;
import com.xu.wxxplatformserver.pojo.SysMenu;
import com.xu.wxxplatformserver.pojo.SysRole;
import com.xu.wxxplatformserver.pojo.SysUser;
import com.xu.wxxplatformserver.mapper.SysUserMapper;
import com.xu.wxxplatformserver.service.ISysUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xu.wxxplatformserver.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 旭日
 * @since 2021-11-08
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private SysRoleServiceImpl sysRoleService;

    @Autowired
    private SysMenuServiceImpl sysMenuService;

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public SysUser getByUsername(String username) {
        return getOne(new QueryWrapper<SysUser>().eq("username",username));
    }

    @Override
    public String getUserAuthorityInfo(Long userId) {

        SysUser sysUser = getById(userId);

        // ROLE_admin system:user:list 这里的权限有角色还有相关菜单权限
        String authority = "";

        // 当前登录的角色存在权限缓存
        if (redisUtil.hasKey(CommonConst.GRANTED_AUTHORITY + sysUser.getUsername())) {
            authority = String.valueOf(redisUtil.get(CommonConst.GRANTED_AUTHORITY + sysUser.getUsername()));
        } else {
            // 得到当前登录用户的角色
            List<SysRole> sysRoleList = sysRoleService.getSysRoleListByUserId(userId);
            if (sysRoleList.size() > 0) {
                String roleCodes = sysRoleList.stream().map(sysRole -> "Role_" + sysRole.getCode()).collect(Collectors.joining(","));
                authority = roleCodes.concat(",");
            }

            // 得到当前登录用户的菜单
            List<SysMenu> sysMenuList = sysMenuService.getSysMenuListByUserId(userId);
            if (sysMenuList.size() > 0) {
                String perms = sysMenuList.stream().map(SysMenu::getPerms).collect(Collectors.joining(","));
                authority = authority.concat(perms);
            }
            redisUtil.set(CommonConst.GRANTED_AUTHORITY + sysUser.getUsername(), authority, 60 * 60);
        }
        return authority;
    }

    @Override
    public List<SysUser> getUserListByRoleId(Long roleId) {
        return list(new QueryWrapper<SysUser>().inSql("user_id", "SELECT user_id FROM sys_user_role  WHERE role_id = " + roleId));
    }

    @Override
    public List<SysUser> getUserListByMenuId(Long menuId) {
        return sysUserMapper.getSysUserListByMenuId(menuId);
    }

    @Override
    public void clearUserAuthorityInfo(String username) {
        redisUtil.del(CommonConst.GRANTED_AUTHORITY + username);
    }

    @Override
    public void clearUserAuthorityInfoByRoleId(Long roleId) {
        List<SysUser> sysUserList = getUserListByRoleId(roleId);
        sysUserList.forEach(sysUser -> clearUserAuthorityInfo(sysUser.getUsername()));
    }

    @Override
    public void clearUserAuthorityInfoByMenuId(Long menuId) {
        List<SysUser> sysUserList = getUserListByMenuId(menuId);
        sysUserList.forEach(sysUser -> clearUserAuthorityInfo(sysUser.getUsername()));
    }


}
