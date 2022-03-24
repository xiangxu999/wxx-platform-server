package me.xu.modules.security.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import me.xu.annotation.Log;
import me.xu.common.Result;
import me.xu.modules.security.service.AuthorizationService;
import me.xu.modules.security.service.dto.UserDto;
import me.xu.modules.security.service.vo.CodeVO;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


/**
 * Description 登录前端控制器
 * Date 2021/11/10 16:48
 * Version 1.0.1
 *
 * @author Wen
 */
@Api(tags = "登录授权")
@RestController
@RequestMapping(value = "/auth")
public class AuthorizationController {

    @Resource
    AuthorizationService authorizationService;

    @Log(value = "登录操作")
    @ApiOperation(value = "登录")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Result login(@RequestBody @Validated UserDto userDto) {
        return authorizationService.login(userDto);
    }

    @ApiOperation(value = "用户信息")
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    @SaCheckLogin
    public Result userInfo(@RequestParam(value = "token") String token) {
        return authorizationService.userInfo(token);
    }

    @ApiOperation(value = "获取验证码")
    @RequestMapping(value = "/code", method = RequestMethod.GET)
    public CodeVO getCode() {
        return authorizationService.getCode();
    }


    @ApiOperation(value = "退出登录")
    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    @SaCheckLogin
    public void logout() {
        authorizationService.logout();
    }
}
