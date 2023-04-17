package me.xu.common;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * Description 通用查询参数
 * Date 2023/4/14 16:15
 * Version 1.0.1
 *
 * @author Wen
 */
@Data
public class QueryParam {
    @ApiModelProperty(value = "字段名称,为空或者*表示所有字段(id, name)")
    private String fields;

    @ApiModelProperty(value = "排序字段(id desc, name)")
    private String order;

    @ApiModelProperty(value = "过滤条件(key - 过滤字段 value - 过滤条件)")
    private List<FilterParam> filter;

    @ApiModelProperty(value = "查询结果分组(id, name)")
    private String group;

    @ApiModelProperty(value = "如果有分页,拿第几页")
    private Integer page = 1;

    @ApiModelProperty(value = "如果有分页,每页数据行数")
    private Integer size = 10;

}
