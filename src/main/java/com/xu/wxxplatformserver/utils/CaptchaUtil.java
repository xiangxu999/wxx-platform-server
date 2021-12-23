package com.xu.wxxplatformserver.utils;

import cn.hutool.core.util.StrUtil;
import com.wf.captcha.*;
import com.wf.captcha.base.Captcha;
import com.xu.wxxplatformserver.exception.BadConfigurationException;
import com.xu.wxxplatformserver.properties.CaptchaProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.awt.*;

/**
 * Description 验证码工具类
 * Date 2021/12/23 11:07
 * Version 1.0.1
 *
 * @author Wen
 */
@Component
public class CaptchaUtil {

    @Autowired
    private CaptchaProperties captchaProperties;

    /**
     * 获取验证码生产类
     *
     * @return /
     */
    public Captcha getCaptcha() {
        return switchCaptcha(captchaProperties);
    }

    /**
     * 依据配置信息生产验证码
     *
     * @param captchaProperties 验证码配置信息
     * @return /
     */
    private Captcha switchCaptcha(CaptchaProperties captchaProperties) {
        Captcha captcha;
        synchronized (this) {
            switch (captchaProperties.getCodeType()) {
                case arithmetic:
                    // 算术类型 https://gitee.com/whvse/EasyCaptcha
                    captcha = new FixedArithmeticCaptcha(captchaProperties.getWidth(), captchaProperties.getHeight());
                    // 几位数运算，默认是两位
                    captcha.setLen(captchaProperties.getLength());
                    break;
                case chinese:
                    captcha = new ChineseCaptcha(captchaProperties.getWidth(), captchaProperties.getHeight());
                    captcha.setLen(captchaProperties.getLength());
                    break;
                case chinese_gif:
                    captcha = new ChineseGifCaptcha(captchaProperties.getWidth(), captchaProperties.getHeight());
                    captcha.setLen(captchaProperties.getLength());
                    break;
                case gif:
                    captcha = new GifCaptcha(captchaProperties.getWidth(), captchaProperties.getHeight());
                    captcha.setLen(captchaProperties.getLength());
                    break;
                case spec:
                    captcha = new SpecCaptcha(captchaProperties.getWidth(), captchaProperties.getHeight());
                    captcha.setLen(captchaProperties.getLength());
                    break;
                default:
                    throw new BadConfigurationException("验证码配置信息错误！");
            }
        }
        if(StrUtil.isNotBlank(captchaProperties.getFontName())){
            captcha.setFont(new Font(captchaProperties.getFontName(), Font.PLAIN, captchaProperties.getFontSize()));
        }
        return captcha;
    }

    static class FixedArithmeticCaptcha extends ArithmeticCaptcha {
        public FixedArithmeticCaptcha(int width, int height) {
            super(width, height);
        }

        @Override
        protected char[] alphas() {
            // 生成随机数字和运算符
            int n1 = num(1, 10), n2 = num(1, 10);
            int opt = num(3);

            // 计算结果
            int res = new int[]{n1 + n2, n1 - n2, n1 * n2}[opt];
            // 转换为字符运算符
            char optChar = "+-x".charAt(opt);

            this.setArithmeticString(String.format("%s%c%s=?", n1, optChar, n2));
            this.chars = String.valueOf(res);

            return chars.toCharArray();
        }
    }
}
