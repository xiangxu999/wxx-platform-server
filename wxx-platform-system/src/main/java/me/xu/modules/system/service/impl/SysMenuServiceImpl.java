package me.xu.modules.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import me.xu.modules.system.mapper.SysMenuMapper;
import me.xu.modules.system.pojo.SysMenu;
import me.xu.modules.system.service.SysMenuService;
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
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {

    @Autowired
    private SysMenuMapper sysMenuMapper;

    @Override
    public List<SysMenu> getCurrentUserNav(Long userId) {
        // 获取菜单列表
        List<SysMenu> sysMenus = getSysMenuListByUserId(userId);
        // 把菜单集合转换为树形结构
        return buildTreeMenu(sysMenus);
    }

    @Override
    public List<SysMenu> getSysMenuListByRoleId(Long roleId) {
        return list(new QueryWrapper<SysMenu>().inSql("menu_id", "SELECT menu_id FROM sys_role_menu  WHERE role_id = " + roleId));
    }

    @Override
    public List<SysMenu> getSysMenuListByUserId(Long userId) {
        List<SysMenu> sysMenuList = sysMenuMapper.getSysMenuIdsByUserId(userId);
        if (sysMenuList.size() > 0) {
            return sysMenuList;
        } else {
            return new ArrayList<>();
        }
    }

    /**
     * 把菜单转换为树形菜单
     * @param menus 菜单
     * @return 树形菜单
     */
    public List<SysMenu> buildTreeMenu(List<SysMenu> menus) {

        List<SysMenu> treeMenu = new ArrayList<>();

        // 先各自寻找各自的孩子
        for (SysMenu menu : menus) {
            for (SysMenu e : menus) {
                if (e.getPid().equals(menu.getMenuId())) {
                    menu.getChildren().add(e);
                }
            }
            // 提取父节点
            if (menu.getPid() == 0L) {
                treeMenu.add(menu);
            }
        }
        return treeMenu;
    }
}
