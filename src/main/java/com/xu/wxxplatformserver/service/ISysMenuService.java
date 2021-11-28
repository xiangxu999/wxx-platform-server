package com.xu.wxxplatformserver.service;

import com.xu.wxxplatformserver.pojo.SysMenu;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xu.wxxplatformserver.pojo.SysUser;

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

}
