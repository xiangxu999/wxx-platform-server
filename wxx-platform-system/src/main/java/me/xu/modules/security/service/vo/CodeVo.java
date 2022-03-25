package me.xu.modules.security.service.vo;

import lombok.Data;

/**
 * Description 验证码Vo
 * Date 2022/3/24 14:41
 * Version 1.0.1
 *
 * @author Wen
 */
@Data
public class CodeVo {

    /**
     * 验证码uuid
     */
    private String uuid;

    /**
     * 图片
     */
    private String img;

    /**
     * 是否开启验证码
     */
    private Integer enabled;

}
