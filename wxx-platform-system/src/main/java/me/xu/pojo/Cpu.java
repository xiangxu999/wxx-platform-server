package me.xu.pojo;

import cn.hutool.core.util.NumberUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Description Cpu
 * Date 2021/12/10 10:44
 * Version 1.0.1
 *
 * @author Wen
 */
@Data
@ApiModel(value = "Cpu", description = "处理器")
public class Cpu {

    /**
     * cpu的名称
     */
    @ApiModelProperty(value = "CPU名称")
    private String cpuName;

    /**
     * 物理CPU核心数
     */
    @ApiModelProperty(value = "物理CPU核心数")
    private int cpuPhysicalNum;

    /**
     * cpu个数
     */
    @ApiModelProperty(value = "CPU个数")
    private int cpuPackage;

    /**
     * 逻辑CPU核心数
     */
    @ApiModelProperty(value = "逻辑CPU核心数")
    private int cpuLogicalNum;

    /**
     * CPU总的使用率
     */
    @ApiModelProperty(value = "CPU总的使用率")
    private double total;

    /**
     * CPU系统使用率
     */
    @ApiModelProperty(value = "CPU系统使用率")
    private double sysRate;

    /**
     * CPU用户使用率
     */
    @ApiModelProperty(value = "CPU用户使用率")
    private double usedRate;

    /**
     * CPU当前等待率
     */
    @ApiModelProperty(value = "CPU当前等待率")
    private double wait;

    /**
     * CPU当前空闲率
     */
    @ApiModelProperty(value = "CPU当前空闲率")
    private double free;

    private double totalRate;

    public double getTotal() {
        return NumberUtil.round(NumberUtil.mul(total, 100), 2).doubleValue();
    }


    public double getSysRate() {
        return NumberUtil.round(NumberUtil.mul(sysRate / total, 100), 2).doubleValue();
    }


    public double getUsedRate() {
        return NumberUtil.round(NumberUtil.mul(usedRate / total, 100), 2).doubleValue();
    }

    public double getWait() {
        return NumberUtil.round(NumberUtil.mul(wait / total, 100), 2).doubleValue();
    }


    public double getFree() {
        return NumberUtil.round(NumberUtil.mul(free / total, 100), 2).doubleValue();
    }

    public double getTotalRate() {
        return NumberUtil.round(getSysRate() + getUsedRate(), 2).doubleValue();
    }
}
