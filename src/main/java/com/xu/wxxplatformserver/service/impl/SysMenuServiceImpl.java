package com.xu.wxxplatformserver.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xu.wxxplatformserver.pojo.SysMenu;
import com.xu.wxxplatformserver.mapper.SysMenuMapper;
import com.xu.wxxplatformserver.pojo.SysUser;
import com.xu.wxxplatformserver.service.ISysMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 旭日
 * @since 2021-11-08
 */
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements ISysMenuService {

    @Autowired
    private SysMenuMapper sysMenuMapper;

    @Override
    public List<SysMenu> getCurrentUserNav(SysUser sysUser) {
        List<SysMenu> sysMenus = getSysMenuListByUserId(sysUser.getUserId());
        // 把菜单集合转换为树形结构
        List<SysMenu> menuTree = buildTreeMenu(sysMenus);

        return menuTree;
    }

    @Override
    public List<SysMenu> getSysMenuListByRoleId(Long roleId) {
        return list(new QueryWrapper<SysMenu>().inSql("menu_id", "SELECT menu_id FROM sys_role_menu  WHERE role_id = " + roleId));
    }

    public List<SysMenu> buildTreeMenu(List<SysMenu> menus) {

        List<SysMenu> finalMenus = new ArrayList<>();

        // 先各自寻找各自的孩子
        for (SysMenu menu : menus) {
            for (SysMenu e : menus) {
                if (e.getPid().equals(menu.getMenuId())) {
                    menu.getChildren().add(e);
                }
            }
            // 提取父节点
            if (menu.getPid() == 0L) {
                finalMenus.add(menu);
            }
        }

        return finalMenus;
    }

    @Override
    public List<SysMenu> getSysMenuListByUserId(Long userId) {
        List<Long> sysMenuIds = sysMenuMapper.getSysMenuIdsByUserId(userId);
        if (sysMenuIds.size() > 0) {
            return listByIds(sysMenuIds);
        } else {
            return new ArrayList<>();
        }
    }
}
