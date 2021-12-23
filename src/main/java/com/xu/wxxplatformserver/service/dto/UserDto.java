package com.xu.wxxplatformserver.service.dto;

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
    private String username;

    @NotBlank
    private String password;

    private String code;

    private String uuid = "";
}
