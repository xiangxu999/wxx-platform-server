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
     * 根据用户id返回用户对象
     * @param userId 用户id
     * @return 用户对象
     */
    SysUser getByUserId(Long userId);

    /**
     * 根据用户名返回用户对象
     * @param username 用户名
     * @return 用户对象
     */
    SysUser getByUsername(String username);

    /**
     * 返回一个账号所拥有的角色标识集合
     * @param userId 用户id
     * @return
     */
    List<Long> getUserRoleList(Long userId);

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
}
