package me.xu.modules.security.service.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import me.xu.modules.system.pojo.SysMenu;

import java.util.List;

/**
 * Description 用户信息VO
 * Date 2022/3/24 15:50
 * Version 1.0.1
 *
 * @author Wen
 */
@Data
@AllArgsConstructor
public class UserInfoVo {

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "头像")
    private String avatar;

    @ApiModelProperty(value = "角色")
    private String role;

    @ApiModelProperty(value = "侧边栏")
    private List<SysMenu> nav;

    @ApiModelProperty(value = "账户创建时间")
    private String created;
}
