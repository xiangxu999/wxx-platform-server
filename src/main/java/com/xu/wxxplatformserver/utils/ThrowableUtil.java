package com.xu.wxxplatformserver.utils;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Description 异常工具
 * Date 2021/12/20 20:56
 * Version 1.0.1
 *
 * @author Wen
 */
public class ThrowableUtil {
    /**
     * 获取堆栈信息
     */
    public static String getStackTrace(Throwable throwable){
        StringWriter sw = new StringWriter();
        try (PrintWriter pw = new PrintWriter(sw)) {
            throwable.printStackTrace(pw);
            return sw.toString();
        }
    }
}
