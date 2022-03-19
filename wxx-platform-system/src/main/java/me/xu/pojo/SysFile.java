package me.xu.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Description 系统文件相关信息实体
 * Date 2021/12/10 10:47
 * Version 1.0.1
 *
 * @author Wen
 */
@Data
@ApiModel(value="SysFile", description="系统文件相关信息实体")
public class SysFile {
    /**
     * 盘符路径
     */
    @ApiModelProperty(value = "盘符路径")
    private String dirName;

    /**
     * 盘符类型
     */
    @ApiModelProperty(value = "盘符类型")
    private String sysTypeName;

    /**
     * 文件类型
     */
    @ApiModelProperty(value = "文件类型")
    private String typeName;

    /**
     * 总大小
     */
    @ApiModelProperty(value = "总大小")
    private double total;

    /**
     * 剩余大小
     */
    @ApiModelProperty(value = "剩余大小")
    private double free;

    /**
     * 已经使用量
     */
    @ApiModelProperty(value = "已经使用量")
    private double used;

    /**
     * 资源的使用率
     */
    @ApiModelProperty(value = "资源的使用率")
    private double usage;
}
