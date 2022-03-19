package me.xu.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Description 系统相关信息实体
 * Date 2021/12/10 10:47
 * Version 1.0.1
 *
 * @author Wen
 */
@Data
@ApiModel(value="Sys", description="系统相关信息实体")
public class Sys {
    /**
     * 服务器名称
     */
    @ApiModelProperty(value = "服务器名称")
    private String computerName;

    /**
     * 服务器Ip
     */
    @ApiModelProperty(value = "服务器Ip")
    private String computerIp;

    /**
     * 项目路径
     */
    @ApiModelProperty(value = "项目路径")
    private String userDir;

    /**
     * 操作系统
     */
    @ApiModelProperty(value = "操作系统")
    private String osName;

    /**
     * 系统架构
     */
    @ApiModelProperty(value = "系统架构")
    private String osArch;
}
