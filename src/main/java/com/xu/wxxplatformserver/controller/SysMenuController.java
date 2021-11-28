package com.xu.wxxplatformserver.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.xu.wxxplatformserver.common.Result;
import com.xu.wxxplatformserver.pojo.SysMenu;
import com.xu.wxxplatformserver.pojo.SysRoleMenu;
import com.xu.wxxplatformserver.service.impl.SysMenuServiceImpl;
import com.xu.wxxplatformserver.service.impl.SysRoleMenuServiceImpl;
import com.xu.wxxplatformserver.service.impl.SysUserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 旭日
 * @since 2021-11-08
 */
@RestController
@RequestMapping("/system/menu")
public class SysMenuController {

    @Autowired
    private SysUserServiceImpl sysUserService;

    @Autowired
    private SysMenuServiceImpl sysMenuService;

    @Autowired
    private SysRoleMenuServiceImpl sysRoleMenuService;


    /**
     * 获得所有的菜单转为树形结构
     * @return Result
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('system:menu:list')")
    public Result list(@RequestParam(value = "name", defaultValue = "") String name) {
        List<SysMenu> menus = sysMenuService.list(new QueryWrapper<SysMenu>().eq(StringUtils.isNotBlank(name), "title", name));
        if (menus.size() == 1) {
            return Result.success(menus);
        }
        List<SysMenu> treeMenus = sysMenuService.buildTreeMenu(menus);
        return Result.success(treeMenus);
    }

    /**
     * 获得单条的菜单信息
     * @return Reuslt
     */
    @RequestMapping(value = "/info/{id}", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('system:menu:list')")
    public Result info(@PathVariable(value = "id") Long id) {
        SysMenu result = sysMenuService.getById(id);
        if (result != null) {
            return Result.success(result);
        } else {
            return Result.failed();
        }
    }


    /**
     * 修改一条菜单信息
     * @param sysMenu 菜单实体
     * @return Result
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('system:menu:update')")
    public Result update(@RequestBody SysMenu sysMenu) {
        boolean result = sysMenuService.updateById(sysMenu);
        if (result) {
            // 清除缓存
            sysUserService.clearUserAuthorityInfoByMenuId(sysMenu.getMenuId());
            return Result.success();
        } else {
            return Result.failed();
        }
    }

    /**
     * 添加一条菜单信息
     * @param sysMenu 菜单实体
     * @return Reuslt
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('system:menu:save')")
    public Result save(@RequestBody SysMenu sysMenu) {
        boolean result = sysMenuService.save(sysMenu);
        if (result) {
            return Result.success();
        } else {
            return Result.failed();
        }
    }

    /**
     * 根据id删除对应的菜单
     * @param id 菜单的id
     * @return Result
     */
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('system:menu:delete')")
    @Transactional
    public Result delete(@PathVariable(value = "id") Long id) {
        int count = sysMenuService.count(new QueryWrapper<SysMenu>().eq("pid",id));
        if (count > 0) {
            return Result.failed("请先删除子菜单");
        }
        boolean result = sysMenuService.removeById(id);
        if (result) {
            // 清除缓存
            sysUserService.clearUserAuthorityInfoByMenuId(id);
            // 同步删除中间关联表
            sysRoleMenuService.remove(new QueryWrapper<SysRoleMenu>().eq("menu_id",id));
            return Result.success();
        } else {
            return Result.failed();
        }
    }
}
