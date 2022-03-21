package me.xu.modules.security.common;

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
    ARITHMETIC,
    /**
     * 中文
     */
    CHINESE,
    /**
     * 中文闪图
     */
    CHINESE_GIF,
    /**
     * 闪图
     */
    GIF,
    /**
     * 英文
     */
    SPEC
}