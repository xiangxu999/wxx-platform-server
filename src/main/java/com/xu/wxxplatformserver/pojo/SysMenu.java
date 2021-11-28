package com.xu.wxxplatformserver.pojo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

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
@ApiModel(value="SysMenu对象", description="菜单")
public class SysMenu implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "菜单的组件")
    @TableId(value = "menu_id", type = IdType.AUTO)
    private Long menuId;

    @ApiModelProperty(value = "父菜单ID，一级菜单为0")
    private Long pid;

    @ApiModelProperty(value = "菜单别名（英文）")
    private String name;

    @ApiModelProperty(value = "菜单显示名称（中文）")
    private String title;

    @ApiModelProperty(value = "菜单URL")
    private String path;

    @ApiModelProperty(value = "菜单对应的组件")
    private String component;

    @ApiModelProperty(value = "授权(多个用逗号分隔，如：user:list,user:create)")
    private String perms;

    @ApiModelProperty(value = "类型     0-目录   1-菜单   2-按钮")
    private Integer type;

    @ApiModelProperty(value = "菜单图标")
    private String icon;

    @ApiModelProperty(value = "排序")
    private Integer sort;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime created;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updated;

    @ApiModelProperty(value = "是否启用：0-禁用 1-启用")
    private Integer status;

    @TableField(exist = false)
    private List<SysMenu> children = new ArrayList<>();
}
