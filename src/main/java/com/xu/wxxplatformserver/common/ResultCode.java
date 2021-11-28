package com.xu.wxxplatformserver.common;

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
    FORBIDDEN(403, "没有相关权限");

    /**
     * 编码
     */
    long code;

    /**
     * 消息
     */
    String message;

    ResultCode(long code, String message) {
        this.code = code;
        this.message = message;
    }
}
