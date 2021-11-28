package com.xu.wxxplatformserver.service;

import com.xu.wxxplatformserver.common.Result;
import com.xu.wxxplatformserver.pojo.SysUser;


/**
 * Description 登录、登出、注册接口
 * Date 2021/11/10 16:29
 * Version 1.0.1
 *
 * @author Wen
 */
public interface UserService {

    /**
     * 注册功能
     * @param sysUser 表单
     * @return SysUser对象
     */
    SysUser register(SysUser sysUser);

    /**
     * 登录功能
     * @param username 用户名
     * @param password 密码
     * @return 生成的JWT的token
     */
    String login(String username, String password);

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
}
