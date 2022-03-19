package me.xu.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Description 日志自定义注解
 * Date 2021/12/24 16:37
 * Version 1.0.1
 *
 * @author Wen
 */


@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Log {
    /**
     * 日志描述
     * @return String
     */
    String value() default  "";
}
