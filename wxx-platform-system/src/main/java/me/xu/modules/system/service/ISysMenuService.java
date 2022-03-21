package me.xu.modules.system.service;

import me.xu.modules.system.pojo.SysMenu;
import com.baomidou.mybatisplus.extension.service.IService;
import me.xu.modules.system.pojo.SysUser;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 旭日
 * @since 2021-11-08
 */
public interface ISysMenuService extends IService<SysMenu> {

    /**
     * 根据用户id得到对应的菜单列表
     * @param userId 用户id
     * @return 菜单列表
     */
    List<SysMenu> getSysMenuListByUserId(Long userId);

    /**
     * 用户当前登录用户的菜单
     * @param sysUser 当前登录的用户
     * @return 用户相关的菜单
     */
    List<SysMenu> getCurrentUserNav(SysUser sysUser);

    /**
     * 根据角色获得对应的菜单
     * @param roleId 角色id
     * @return 角色对象的菜单列表
     */
    List<SysMenu> getSysMenuListByRoleId(Long roleId);

}
