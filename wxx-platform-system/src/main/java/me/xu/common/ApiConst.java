package me.xu.common;

/**
 * Description 调用外部的Api
 * Date 2021/12/21 9:52
 * Version 1.0.1
 *
 * @author Wen
 */
public class ApiConst {
    /**
     * 高德
     */
    public static class GaoDe {
        /**
         * 高德-IP定位
         */
        public static String IP_URL = "https://restapi.amap.com/v3/ip?ip=%s&key=1e53b3f90cb7a09b7fd405d4d72a73d2";
    }


    /**
     * Whois
     */
    public static class Whois {

        /**
         * IP定位
         */
        public static String IP_URL = "http://whois.pconline.com.cn/ipJson.jsp?ip=%s&json=true";
    }

}
