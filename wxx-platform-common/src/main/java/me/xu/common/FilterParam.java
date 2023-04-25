package me.xu.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * Description 过滤参数
 * Date 2023/4/17 19:16
 * Version 1.0.1
 *
 * @author Wen
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="FilterParam", description="过滤参数")
public class FilterParam implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "条件的连接(默认为and)")
    private String linkType;

    @ApiModelProperty(value = "条件的字段")
    private String field;

    @ApiModelProperty(value = "条件的类型")
    private String type;

    @ApiModelProperty(value = "条件值")
    private String value;

}
