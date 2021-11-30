package com.xu.wxxplatformserver.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.stp.SaTokenInfo;
import com.xu.wxxplatformserver.common.Result;
import com.xu.wxxplatformserver.pojo.SysUser;
import com.xu.wxxplatformserver.service.impl.UserServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * Description 登录前端控制器
 * Date 2021/11/10 16:48
 * Version 1.0.1
 *
 * @author Wen
 */
@Api(tags = "UserController")
@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private UserServiceImpl userService;

    @ApiOperation(value = "登录")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Result login(@RequestBody SysUser sysUser) {
        SaTokenInfo tokenInfo = userService.login(sysUser.getUsername(), sysUser.getPassword());
        if (tokenInfo == null) {
            return Result.validateFailed("用户名或密码错误");
        }
        return Result.success(tokenInfo,"登录成功");
    }

    @ApiOperation(value = "用户信息")
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    @SaCheckLogin
    public Result userInfo(@RequestParam(value = "token") String token) {
        return userService.userInfo(token);
    }


    @ApiOperation(value = "退出登录")
    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    @SaCheckLogin
    public Result logout() {
        return userService.logout();
    }
}
