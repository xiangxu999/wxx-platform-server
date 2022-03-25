package me.xu.modules.security.service.vo;

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

    /**
     * 用户名
     */
    private String username;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 角色
     */
    private String role;

    /**
     * 侧边栏
     */
    private List<SysMenu> nav;

    /**
     * 账户创建时间
     */
    private String created;
}
