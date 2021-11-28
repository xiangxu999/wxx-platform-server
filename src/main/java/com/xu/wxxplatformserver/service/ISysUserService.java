package com.xu.wxxplatformserver.service;

import com.xu.wxxplatformserver.pojo.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xu.wxxplatformserver.service.impl.SysUserServiceImpl;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 旭日
 * @since 2021-11-08
 */
public interface ISysUserService extends IService<SysUser> {

    /**
     * 根据用户名返回用户对象
     * @param username 用户名
     * @return 用户对象
     */
    SysUser getByUsername(String username);

    /**
     * 根据用户id获取对应的权限信息
     * @param userId 用户id
     * @return 权限信息
     */
    String getUserAuthorityInfo(Long userId);

    /**
     * 根据角色id返回对应的用户列表
     * @param roleId 角色id
     * @return 用户列表
     */
    List<SysUser> getUserListByRoleId(Long roleId);

    /**
     * 根据菜单id返回对应的用户列表
     * @param menuId 菜单id
     * @return 用户列表
     */
    List<SysUser> getUserListByMenuId(Long menuId);

    /**
     * 用户更改的时候清除权限缓存
     * @param username 用户名
     */
    void clearUserAuthorityInfo(String username);


    /**
     * 角色更改的时候清除权限缓存
     * @param roleId 角色id
     */
    void clearUserAuthorityInfoByRoleId(Long roleId);

    /**
     * 菜单更改的时候清除权限缓存
     * @param menuId 菜单id
     */
    void clearUserAuthorityInfoByMenuId(Long menuId);
}
