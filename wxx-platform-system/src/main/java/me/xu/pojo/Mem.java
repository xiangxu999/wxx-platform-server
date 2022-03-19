package me.xu.pojo;

import cn.hutool.core.util.NumberUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Description 內存相关信息实体
 * Date 2021/12/10 10:47
 * Version 1.0.1
 *
 * @author Wen
 */
@Data
@ApiModel(value="Mem", description="內存相关信息实体")
public class Mem {

    /**
     * 内存总量
     */
    @ApiModelProperty(value = "内存总量")
    private double total;

    /**
     * 已用内存
     */
    @ApiModelProperty(value = "已用内存")
    private double used;

    /**
     * 剩余内存
     */
    @ApiModelProperty(value = "剩余内存")
    private double free;

    /**
     * 资源的使用率
     */
    @ApiModelProperty(value = "资源的使用率")
    private double usage;

    public double getTotal() {
        return NumberUtil.div(total, (1024 * 1024 * 1024), 2);
    }

    public double getUsed() {
        return NumberUtil.div(used, (1024 * 1024 * 1024), 2);
    }

    public double getFree() {
        return NumberUtil.div(free, (1024 * 1024 * 1024), 2);
    }

    public double getUsage() {
        return NumberUtil.mul(NumberUtil.div(used, total, 4), 100);
    }
}
