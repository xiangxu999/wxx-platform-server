package com.xu.wxxplatformserver.pojo;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.NumberUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.lang.management.ManagementFactory;
import java.util.Date;

/**
 * Description Jvm
 * Date 2021/12/10 10:45
 * Version 1.0.1
 *
 * @author Wen
 */
@Data
@ApiModel(value="Jvm", description="java虚拟机")
public class Jvm {

    /**
     * 当前JVM占用的内存总数(M)
     */
    @ApiModelProperty(value = "当前JVM占用的内存总数(M)")
    private double total;

    /**
     * JVM最大可用内存总数(M)
     */
    @ApiModelProperty(value = "JVM最大可用内存总数(M)")
    private double max;

    /**
     * JVM空闲内存(M)
     */
    @ApiModelProperty(value = "JVM空闲内存(M)")
    private double free;

    /**
     * JDK版本
     */
    @ApiModelProperty(value = "JDK版本")
    private String version;

    /**
     * JDK路径
     */
    @ApiModelProperty(value = "JDK路径")
    private String home;

    /**
     * JDK启动时间
     */
    @ApiModelProperty(value = "JDK启动时间")
    private String startTime;

    /**
     * JDK运行时间
     */
    @ApiModelProperty(value = "JDK运行时间")
    private String runTime;

    public double getTotal() {
        return NumberUtil.div(total, (1024 * 1024), 2);
    }


    public double getMax() {
        return NumberUtil.div(max, (1024 * 1024), 2);
    }


    public double getFree() {
        return NumberUtil.div(free, (1024 * 1024), 2);
    }


    public double getUsed() {
        return NumberUtil.div(total - free, (1024 * 1024), 2);
    }

    public double getUsage() {
        return NumberUtil.mul(NumberUtil.div(total - free, total, 4), 100);
    }

    /**
     * 获取JDK名称
     */
    public String getName() {
        return ManagementFactory.getRuntimeMXBean().getVmName();
    }

    public String getStartTime() {
        return DateUtil.formatDateTime(new Date(ManagementFactory.getRuntimeMXBean().getStartTime()));
    }

    public String getRunTime() {
        long startTime = ManagementFactory.getRuntimeMXBean().getStartTime();
        return DateUtil.formatBetween(DateUtil.current() - startTime);
    }
}
