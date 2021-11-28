package com.xu.wxxplatformserver.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xu.wxxplatformserver.common.Result;
import com.xu.wxxplatformserver.pojo.SysRole;
import com.xu.wxxplatformserver.pojo.SysRoleMenu;
import com.xu.wxxplatformserver.pojo.SysUserRole;
import com.xu.wxxplatformserver.service.impl.SysRoleMenuServiceImpl;
import com.xu.wxxplatformserver.service.impl.SysRoleServiceImpl;
import com.xu.wxxplatformserver.service.impl.SysUserRoleServiceImpl;
import com.xu.wxxplatformserver.service.impl.SysUserServiceImpl;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 旭日
 * @since 2021-11-08
 */
@RestController
@RequestMapping("/system/role")
public class SysRoleController {

    @Autowired
    private SysRoleServiceImpl sysRoleService;

    @Autowired
    private SysRoleMenuServiceImpl sysRoleMenuService;

    @Autowired
    private SysUserServiceImpl sysUserService;

    @Autowired
    private SysUserRoleServiceImpl sysUserRoleService;

    /**
     * 获得角色列表
     * @return Result
     */
    @ApiOperation(value = "角色列表")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('system:role:list')")
    public Result list(@RequestParam(value = "current", defaultValue = "1") Integer current,
                       @RequestParam(value = "size", defaultValue = "10") Integer size,
                       @RequestParam(value = "name", defaultValue = "") String name) {
        Page<SysRole> page = new Page<>(current, size);
        QueryWrapper<SysRole> queryWrapper = new QueryWrapper<SysRole>().like(StringUtils.isNotBlank(name),"name", name);
        Page<SysRole> result = sysRoleService.page(page, queryWrapper);
        if (result.getRecords().size() > 0) {
            return Result.success(result);
        } else {
            return Result.failed();
        }
    }


    /**
     * 获得单条的角色信息
     * @return Reuslt
     */
    @ApiOperation(value = "单条角色信息")
    @RequestMapping(value = "/info/{id}", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('system:role:list')")
    public Result info(@PathVariable(value = "id") Long id) {
        SysRole result = sysRoleService.getById(id);
        return Result.success(result);
    }

    /**
     * 修改一条角色信息
     * @param sysRole 角色实体
     * @return Reuslt
     */
    @ApiOperation(value = "修改角色信息")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('system:role:update')")
    public Result update(@RequestBody SysRole sysRole) {
        boolean result = sysRoleService.updateById(sysRole);
        if (result) {
            // 清除缓存
            sysUserService.clearUserAuthorityInfoByRoleId(sysRole.getRoleId());
            return Result.success();
        } else {
            return Result.failed();
        }
    }

    /**
     * 添加一条角色信息
     * @param sysRole 角色实体
     * @return Reuslt
     */
    @ApiOperation(value = "添加角色信息")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('system:role:save')")
    public Result save(@RequestBody SysRole sysRole) {
        boolean result = sysRoleService.save(sysRole);
        if (result) {
            return Result.success();
        } else {
            return Result.failed();
        }
    }

    /**
     * 根据ids删除对应的角色
     * @param ids 角色的id
     * @return Result
     */
    @ApiOperation(value = "删除用户角色")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('system:role:delete')")
    @Transactional
    public Result deleteByIds(@RequestBody Long[] ids) {
        boolean result = sysRoleService.removeByIds(Arrays.asList(ids));
        if (result) {
            // 删除中间表信息
            sysRoleMenuService.remove(new QueryWrapper<SysRoleMenu>().in("role_id",ids));
            sysUserRoleService.remove(new QueryWrapper<SysUserRole>().in("role_id",ids));

            // 清理缓存
            Arrays.stream(ids).forEach(id -> {
                sysUserService.clearUserAuthorityInfoByRoleId(id);
            });
            return Result.success();
        } else {
            return Result.failed();
        }
    }

    /**
     * 根据角色id获得对应菜单ids
     * @param id 角色的id
     * @return Result
     */
    @ApiOperation(value = "根据角色获得对应的菜单")
    @RequestMapping(value = "/perm/{id}", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('system:role:list')")
    public Result perm(@PathVariable(value = "id") Long id) {
        SysRole result = sysRoleService.getById(id);
        List<SysRoleMenu> roleMenus = sysRoleMenuService.list(new QueryWrapper<SysRoleMenu>().eq("role_id", id));
        List<Long> menuIds = roleMenus.stream().map(SysRoleMenu::getMenuId).collect(Collectors.toList());
        result.setMenuIds(menuIds);
        return Result.success(result);
    }

    /**
     * 中间表
     * @param id 角色id
     * @param menuIds 菜单id
     * @return Result
     */
    @ApiOperation(value = "SysRoleMenu中间表")
    @RequestMapping(value = "/perm/{id}", method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('system:role:save')")
    public Result submit(@PathVariable(value = "id") Long id, @RequestBody Long[] menuIds) {

        List<SysRoleMenu> sysRoleMenus = new ArrayList<>();

        Arrays.stream(menuIds).forEach(menuId -> {
            SysRoleMenu sysRoleMenu = new SysRoleMenu();
            sysRoleMenu.setRoleId(id);
            sysRoleMenu.setMenuId(menuId);

            sysRoleMenus.add(sysRoleMenu);
        });

        // 删除之前的记录
        sysRoleMenuService.remove(new QueryWrapper<SysRoleMenu>().eq("role_id",id));

        // 添加记录
        sysRoleMenuService.saveBatch(sysRoleMenus);

        // 删除缓存
        sysUserService.clearUserAuthorityInfoByRoleId(id);

        return Result.success();
    }
}
