package com.xu.wxxplatformserver.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.stp.SaTokenInfo;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.wf.captcha.base.Captcha;
import com.xu.wxxplatformserver.annotation.Log;
import com.xu.wxxplatformserver.common.LoginCodeEnum;
import com.xu.wxxplatformserver.common.Result;
import com.xu.wxxplatformserver.exception.BadRequestException;
import com.xu.wxxplatformserver.properties.CaptchaProperties;
import com.xu.wxxplatformserver.service.dto.UserDto;
import com.xu.wxxplatformserver.service.impl.UserServiceImpl;
import com.xu.wxxplatformserver.utils.CaptchaUtil;
import com.xu.wxxplatformserver.utils.RedisUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;


/**
 * Description 登录前端控制器
 * Date 2021/11/10 16:48
 * Version 1.0.1
 *
 * @author Wen
 */
@Api(tags = "登录相关")
@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private CaptchaProperties captchaProperties;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private CaptchaUtil captchaUtil;

    @ApiOperation(value = "登录")
    @Log(value = "登录操作")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Result login(@RequestBody @Validated UserDto userDto) {
        // 开启验证码
        if(captchaProperties.getEnabled()) {
            // 查询验证码
            String code = (String) redisUtil.get(userDto.getUuid());
            // 清除验证码
            redisUtil.del(userDto.getUuid());
            // redis缓存已经过期
            if (StrUtil.isBlank(code)) {
                throw new BadRequestException("验证码不存在或已过期");
            }
            if (StrUtil.isBlank(userDto.getCode()) || !userDto.getCode().equalsIgnoreCase(code)) {
                throw new BadRequestException("验证码错误");
            }
        }
        SaTokenInfo tokenInfo = userService.login(userDto.getUsername(), userDto.getPassword());
        if (tokenInfo == null) {
            throw new BadRequestException("用户名或密码错误");
        }
        return Result.success(tokenInfo,"登录成功");
    }

    @ApiOperation(value = "用户信息")
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    @SaCheckLogin
    public Result userInfo(@RequestParam(value = "token") String token) {
        return userService.userInfo(token);
    }

    @ApiOperation("获取验证码")
    @RequestMapping(value = "/code", method = RequestMethod.GET)
    public Result getCode() {
        if (!captchaProperties.getEnabled()) {
            // 验证码信息
            Map<String, Object> imgResult = new HashMap<String, Object>(2) {{
                put("enabled", 0);
            }};
            return Result.success(imgResult);
        }
        // 获取运算的结果
        Captcha captcha = captchaUtil.getCaptcha();
        String uuid = captchaProperties.getCodeKey() + IdUtil.simpleUUID();
        //当验证码类型为 arithmetic时且长度 >= 2 时，captcha.text()的结果有几率为浮点型
        String captchaValue = captcha.text();
        if (captcha.getCharType() - 1 == LoginCodeEnum.arithmetic.ordinal() && captchaValue.contains(".")) {
            captchaValue = captchaValue.split("\\.")[0];
        }
        // 保存
        redisUtil.set(uuid, captchaValue, captchaProperties.getExpiration(), TimeUnit.MINUTES);
        // 验证码信息
        Map<String, Object> imgResult = new HashMap<String, Object>(2) {{
            put("enabled", 1);
            put("img", captcha.toBase64());
            put("uuid", uuid);
        }};
        return Result.success(imgResult);
    }


    @ApiOperation(value = "退出登录")
    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    @SaCheckLogin
    public Result logout() {
        return userService.logout();
    }
}
