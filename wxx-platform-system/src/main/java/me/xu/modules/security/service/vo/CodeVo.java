package me.xu.modules.security.service.vo;

import io.swagger.annotations.ApiModelProperty;
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

    @ApiModelProperty(value = "验证码uuid")
    private String uuid;

    @ApiModelProperty(value = "图片")
    private String img;

    @ApiModelProperty(value = "是否开启验证码")
    private Integer enabled;

}
