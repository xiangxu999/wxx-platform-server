package me.xu.utils;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * Description 获取 HttpServletRequest
 * Date 2021/12/20 16:45
 * Version 1.0.1
 *
 * @author Wen
 */
public class RequestHolder {

    public static HttpServletRequest getHttpServletRequest() {
        // 得到request
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = Objects.requireNonNull(attributes).getRequest();
        return request;
    }
}
