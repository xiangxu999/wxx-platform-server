package com.xu.wxxplatformserver.service.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * Description
 * Date 2021/12/23 14:00
 * Version 1.0.1
 *
 * @author Wen
 */
@Data
public class UserDto {

    @NotBlank
    @ApiModelProperty(value = "用户名")
    private String username;

    @NotBlank
    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "验证码")
    private String code;

    @ApiModelProperty(value = "唯一id")
    private String uuid = "";
}
