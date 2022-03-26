package me.xu.modules.system.controller;


import cn.dev33.satoken.annotation.SaCheckPermission;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import me.xu.annotation.Log;
import me.xu.modules.system.pojo.SysUser;
import me.xu.modules.system.service.SysUserService;
import me.xu.modules.system.service.dto.UserQueryParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author 旭日
 * @since 2021-11-08
 */
@Api(tags = "系统用户")
@RestController
@RequestMapping("/system/user")
public class SysUserController {

    @Autowired
    private SysUserService sysUserService;

    @ApiOperation(value = "用户列表")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @SaCheckPermission("system:user:list")
    public Page<SysUser> userList(@RequestParam(value = "current", defaultValue = "1") Integer current,
                                  @RequestParam(value = "size", defaultValue = "10") Integer size,
                                  UserQueryParam queryParam) {
        return sysUserService.userList(new Page<>(current, size), queryParam);
    }


    @ApiOperation(value = "单条用户信息")
    @ApiImplicitParam(name = "id", value = "用户id")
    @RequestMapping(value = "/info/{id}", method = RequestMethod.GET)
    @SaCheckPermission("system:user:list")
    public SysUser userInfo(@PathVariable(value = "id") Long id) {
        return sysUserService.userInfo(id);
    }


    @Log("新增用户操作")
    @ApiOperation(value = "新增用户")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @SaCheckPermission("system:user:save")
    public Boolean userSave(@RequestBody SysUser sysUser) {
        return sysUserService.userSave(sysUser);
    }


    @Log("修改用户操作")
    @ApiOperation(value = "修改用户信息")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @SaCheckPermission("system:user:update")
    public Boolean userUpdate(@RequestBody SysUser sysUser) {
        return sysUserService.userUpdate(sysUser);
    }


    @Log("删除用户操作")
    @ApiOperation(value = "删除用户信息")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @SaCheckPermission("system:user:delete")
    public Boolean userDelete(@RequestBody Long[] ids) {
        return sysUserService.userDelete(ids);
    }

    @Log("分配角色操作")
    @ApiOperation(value = "分配角色")
    @ApiImplicitParam(name = "id", value = "用户id")
    @RequestMapping(value = "/role/{id}", method = RequestMethod.POST)
    @SaCheckPermission("system:user:update")
    public Boolean roleChange(@PathVariable(value = "id") Long id, @RequestBody Long[] roleIds) {
        return sysUserService.roleChange(id, roleIds);
    }

    @Log("重置密码操作")
    @ApiOperation(value = "重置密码")
    @ApiImplicitParam(name = "id", value = "用户id")
    @RequestMapping(value = "/repass/{id}", method = RequestMethod.POST)
    @SaCheckPermission("system:user:update")
    public Boolean repass(@PathVariable(value = "id") Long id) {
        return sysUserService.repass(id);
    }


}
