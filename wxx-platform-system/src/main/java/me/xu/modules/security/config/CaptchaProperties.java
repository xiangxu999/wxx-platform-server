package me.xu.modules.security.config;

import me.xu.modules.security.common.LoginCodeEnum;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Description 验证码配置
 * Date 2021/12/23 10:19
 * Version 1.0.1
 *
 * @author Wen
 */
@Component
@ConfigurationProperties(prefix = "login.captcha")
@Data
public class CaptchaProperties {
    /**
     * 是否启用验证码
     */
    private Boolean enabled = true;
    /**
     * 验证码 key
     */
    private String codeKey;
    /**
     * 验证码配置
     */
    private LoginCodeEnum codeType;
    /**
     * 验证码有效期 分钟
     */
    private Long expiration;
    /**
     * 验证码内容长度
     */
    private int length;
    /**
     * 验证码宽度
     */
    private int width;
    /**
     * 验证码高度
     */
    private int height;
    /**
     * 验证码字体
     */
    private String fontName;
    /**
     * 字体大小
     */
    private int fontSize;
}
