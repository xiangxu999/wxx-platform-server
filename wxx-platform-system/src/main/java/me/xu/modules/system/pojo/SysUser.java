package me.xu.modules.system.pojo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 
 * </p>
 *
 * @author 旭日
 * @since 2021-11-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="SysUser", description="系统用户")
public class SysUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "user_id", type = IdType.AUTO)
    @ApiModelProperty(value = "用户主键")
    private Long userId;

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "性别：1-男 0-女")
    private Integer gender;

    @ApiModelProperty(value = "头像路径")
    private String avatar;

    @ApiModelProperty(value = "电话号码")
    private String tel;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime created;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updated;

    @ApiModelProperty(value = "最后一次登录的时间")
    private LocalDateTime lastLogin;

    @ApiModelProperty(value = "状态：1-启用 0-禁用")
    private Integer status;

    @ApiModelProperty(value = "是否是管理员：1-是 0-不是")
    private Integer isAdmin;

    @ApiModelProperty(value = "重置密码时间")
    private LocalDateTime pwdResetTime;

    @TableField(exist = false)
    List<SysRole> roles = new ArrayList<>();


}
