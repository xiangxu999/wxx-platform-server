package me.xu.modules.security.service;

import me.xu.common.Result;
import me.xu.modules.security.service.dto.UserDto;
import me.xu.modules.system.pojo.SysUser;


/**
 * Description 登录、登出、注册接口
 * Date 2021/11/10 16:29
 * Version 1.0.1
 *
 * @author Wen
 */
public interface AuthorizationService {

    /**
     * 注册功能
     * @param sysUser 表单
     * @return SysUser对象
     */
    SysUser register(SysUser sysUser);

    /**
     * 登录功能
     * @param userDto 登录表单
     * @return Result
     */
    Result login(UserDto userDto);

    /**
     * 得到当前用户一些基础信息
     * @param token 前端传来的token
     * @return Result
     */
    Result userInfo(String token);

    /**
     * 用户退出
     * @return Result
     */
    Result logout();


    /**
     * 获取验证码
     * @return Result
     */
    Result getCode();


}
