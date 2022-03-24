package me.xu.common;

import lombok.Getter;

/**
 * Description 常用操作code和message
 * Date 2021/11/9 15:40
 * Version 1.0.1
 *
 * @author Wen
 */
@Getter
public enum ResultCode {

    /**
     * 操作成功
     */
    SUCCESS(200, "操作成功"),

    /**
     * 操作失败
     */
    FAILED(500, "操作失败"),

    /**
     * 参数检验失败
     */
    VALIDATE_FAILED(501, "参数检验失败"),

    /**
     * 暂未登录或token已经过期
     */
    UNAUTHORIZED(401, "暂未登录或登录过期"),

    /**
     * 尚未授权
     */
    FORBIDDEN(403, "没有相关权限"),

    /**
     * 验证码配置信息错误
     */
    CODE_CONFIG_ERROR(1001, "验证码配置信息错误"),

    /**
     * 验证码不存在或已过期
     */
    CODE_EXPIRE(1002, "验证码不存在或已过期"),

    /**
     * 验证码错误
     */
    CODE_ERROR(1003, "验证码错误"),

    /**
     * 账号或密码错误
     */
    LOGIN_ERROR(1011, "账号或密码错误");


    /**
     * 编码
     */
    final Integer code;

    /**
     * 消息
     */
    final String message;

    ResultCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
