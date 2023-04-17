package me.xu.common;

import lombok.Data;
import me.xu.consts.QueryConst;

/**
 * Description 过滤参数
 * Date 2023/4/17 19:16
 * Version 1.0.1
 *
 * @author Wen
 */
@Data
public class FilterParam {
    /**
     * 条件的连接
     * 默认为and
     */
    private String linkType;

    /**
     * 条件的字段
     */
    private String field;

    /**
     * 条件的类型
     */
    private String type;

    /**
     * 条件值
     */
    private String value;

    public FilterParam(String linkType, String field, String type, String value) {
        this.linkType = linkType;
        this.field = field;
        this.type = type;
        this.value = value;
    }

    public FilterParam(String field, String type, String value) {
        this(QueryConst.Link.LINK_AND, field, type, value);
    }

}
