package me.xu.modules.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import me.xu.modules.system.pojo.SysMenu;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author 旭日
 * @since 2021-11-08
 */
public interface SysMenuService extends IService<SysMenu> {

    /**
     * 根据用户id得到对应的菜单列表
     *
     * @param userId 用户id
     * @return 菜单列表
     */
    List<SysMenu> getSysMenuListByUserId(Long userId);

    /**
     * 根据用户id得到对应的树形菜单
     *
     * @param userId 用户id
     * @return 树形菜单
     */
    List<SysMenu> getCurrentUserNav(Long userId);

    /**
     * 根据角色id获得对应的菜单
     *
     * @param roleId 角色id
     * @return 角色对象的菜单列表
     */
    List<SysMenu> getSysMenuListByRoleId(Long roleId);



}
