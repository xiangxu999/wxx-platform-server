package com.xu.wxxplatformserver.common;

/**
 * Description 验证码类型枚举
 * Date 2021/12/23 11:46
 * Version 1.0.1
 *
 * @author Wen
 */

public enum LoginCodeEnum {
    /**
     * 算数
     */
    arithmetic,
    /**
     * 中文
     */
    chinese,
    /**
     * 中文闪图
     */
    chinese_gif,
    /**
     * 闪图
     */
    gif,
    /**
     * 英文
     */
    spec
}