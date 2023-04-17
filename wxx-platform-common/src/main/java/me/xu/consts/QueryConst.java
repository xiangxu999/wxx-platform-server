package me.xu.consts;

/**
 * Description 查询配置常量
 * Date 2023/4/17 19:32
 * Version 1.0.1
 *
 * @author Wen
 */
public class QueryConst {

    /**
     * 连接条件
     */
    public static class Link {

        /**
         * AND连接
         */
        public static final String LINK_AND = "AND";

        /**
         * OR连接
         */
        public static final String LINK_OR = "OR";

    }

    /**
     * 排序
     */
    public static class Order {

        /**
         * 升序
         */
        public static final String ORDER_ASC = "ASC";

        /**
         * 降序
         */
        public static final String ORDER_DESC = "DESC";

    }

    /**
     * 条件类型
     */
    public static class FilterType {

        /**
         * 相等
         */
        public static final String EQUAL = "=";

        /**
         * 不等于
         */
        public static final String NOT_EQUAL = "!=";


        /**
         * 大于等于
         */
        public static final String GREATER_THAN = ">=";

        /**
         * 大于
         */
        public static final String GREATER_THAN_GT = ">";

        /**
         * 小于等于
         */
        public static final String LESS_THAN = "<=";

        /**
         * 小于
         */
        public static final String LESS_THAN_NQ = "<";


        /**
         * 中模糊查询
         */
        public static final String LIKE = "LIKE";

        /**
         * 左模糊查询
         */
        public static final String LEFT_LIKE = "LEFT_LIKE";

        /**
         * 右模糊查询
         */
        public static final String RIGHT_LIKE = "RIGHT_LIKE";

        /**
         *  包含
         */
        public static final String IN = "IN";

        /**
         * 不包含
         */
        public static final String NOT_IN = "NOT_IN";

        /**
         * 不为空
         */
        public static final String NOT_NULL = "NOT_NULL";

        /**
         * 为空
         */
        public static final String IS_NULL = "IS_NULL";

        /**
         * between
         */
        public static final String BETWEEN = "BETWEEN";
    }
}
