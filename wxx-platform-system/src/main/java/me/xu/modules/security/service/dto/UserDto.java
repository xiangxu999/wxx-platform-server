package me.xu.modules.security.service.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * Description
 * Date 2021/12/23 14:00
 * Version 1.0.1
 *
 * @author Wen
 */
@Data
public class UserDto {

    @NotBlank(message = "用户名不能为空")
    @ApiModelProperty(value = "用户名")
    private String username;

    @NotBlank(message = "密码不能为空")
    @Size(min = 6, max = 11, message = "密码长度必须是6-11个字符")
    @ApiModelProperty(value = "密码")
    private String password;


    @NotBlank(message = "验证码不能为空")
    @ApiModelProperty(value = "验证码")
    private String code;

    @ApiModelProperty(value = "唯一id")
    private String uuid = "";
}
