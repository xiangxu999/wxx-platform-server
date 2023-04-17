package me.xu.utils;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import me.xu.common.FilterParam;
import me.xu.common.QueryParam;
import me.xu.consts.QueryConst;

import java.util.List;

/**
 * Description QueryWrapper生成工具
 * Date 2023/4/14 16:38
 * Version 1.0.1
 *
 * @author Wen
 */
public class QueryParamUtil {


    /**
     * 防止外部实例化
     */
    private QueryParamUtil() {

    }

    /**
     * 根据QueryParam生成QueryWrapper
     * @param queryParam 查询参数
     * @return QueryWrapper
     */
    public static <T> QueryWrapper<T> generateQueryWrapper(QueryParam queryParam) {
        QueryWrapper<T> queryWrapper = new QueryWrapper<>();
        // 查询字段配置
        configFields(queryWrapper, queryParam);
        // 查询条件配置
        configFilter(queryWrapper, queryParam);
        // 排序配置
        configOrder(queryWrapper, queryParam);
        return queryWrapper;
    }

    /**
     * 根据QueryParam生成Page
     * @param queryParam 查询参数
     * @return Page
     */
    public static <T> Page<T> generatePage(QueryParam queryParam) {
        return new Page<>(queryParam.getPage(), queryParam.getSize());
    }

    /**
     * 查询字段配置
     * @param queryWrapper QueryWrapper
     * @param queryParam 查询参数
     */
    private static <T> void configFields(QueryWrapper<T> queryWrapper, QueryParam queryParam) {
        // 如果为空，默认查询全部字段
        if (StrUtil.isEmpty(queryParam.getFields())) {
            queryWrapper.select("*");
        } else {
            queryWrapper.select(queryParam.getFields());
        }
    }

    /**
     * 条件字段配置
     * @param queryWrapper QueryWrapper
     * @param queryParam 查询参数
     */
    private static <T> void configFilter(QueryWrapper<T> queryWrapper, QueryParam queryParam) {
        // 获取查询Map
        List<FilterParam> filter = queryParam.getFilter();
        filter.forEach(item -> {
            // 连接条件
            if (QueryConst.Link.LINK_OR.equalsIgnoreCase(item.getLinkType())) {
                queryWrapper.or();
            }
            // 条件字段
            String field = item.getField();
            // 条件数值
            String val = item.getValue();
            switch (item.getType().toUpperCase()) {
                case QueryConst.FilterType.EQUAL:
                    queryWrapper.eq(field, val);
                    break;
                case QueryConst.FilterType.NOT_EQUAL:
                    queryWrapper.ne(field, val);
                    break;
                case QueryConst.FilterType.GREATER_THAN:
                    queryWrapper.ge(field, val);
                    break;
                case QueryConst.FilterType.GREATER_THAN_GT:
                    queryWrapper.gt(field, val);
                    break;
                case QueryConst.FilterType.LESS_THAN:
                    queryWrapper.le(field, val);
                    break;
                case QueryConst.FilterType.LESS_THAN_NQ:
                    queryWrapper.lt(field, val);
                    break;
                case QueryConst.FilterType.LIKE:
                    queryWrapper.like(field, val);
                    break;
                case QueryConst.FilterType.LEFT_LIKE:
                    queryWrapper.likeLeft(field, val);
                    break;
                case QueryConst.FilterType.RIGHT_LIKE:
                    queryWrapper.likeRight(field, val);
                    break;
                case QueryConst.FilterType.IN:
                    if (StrUtil.isNotEmpty(val)) {
                        queryWrapper.in(field, val);
                    }
                    break;
                case QueryConst.FilterType.NOT_IN:
                    if (StrUtil.isNotEmpty(val)) {
                        queryWrapper.notIn(field, val);
                    }
                    break;
                case QueryConst.FilterType.NOT_NULL:
                    queryWrapper.isNotNull(field);
                    break;
                case QueryConst.FilterType.IS_NULL:
                    queryWrapper.isNull(field);
                    break;
                case QueryConst.FilterType.BETWEEN:
                    String[] between = val.split(",");
                    queryWrapper.between(field, between[0], between[1]);
                    break;
                default:
                    break;
            }
        });
    }

    /**
     * 排序字段配置
     * @param queryWrapper QueryWrapper
     * @param queryParam 查询参数
     */
    private static <T> void configOrder(QueryWrapper<T> queryWrapper, QueryParam queryParam) {
        String order = queryParam.getOrder();
        String[] groupArray = order.split(",");
        for (String orderItem : groupArray) {
            String orderField = StrUtil.subBefore(orderItem.trim(), " ", false);
            String orderType = StrUtil.subAfter(orderItem.trim(), " ", false);
            queryWrapper.orderBy(true, !QueryConst.Order.ORDER_DESC.equalsIgnoreCase(orderType), orderField);
        }
    }

    /**
     * 分组配置
     * @param queryWrapper QueryWrapper
     * @param queryParam 查询参数
     */
    private static <T> void configGroup(QueryWrapper<T> queryWrapper, QueryParam queryParam) {
        queryWrapper.groupBy(queryParam.getGroup());
    }
}
