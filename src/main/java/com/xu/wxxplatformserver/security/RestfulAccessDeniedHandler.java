package com.xu.wxxplatformserver.security;

import cn.hutool.json.JSONUtil;
import com.xu.wxxplatformserver.common.Result;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Description 当访问接口没有权限时，自定义的返回结果
 * Date 2021/11/9 16:55
 * Version 1.0.1
 *
 * @author Wen
 */
@Component
public class RestfulAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) throws IOException, ServletException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.getWriter().println(JSONUtil.parse(Result.forbidden(e.getMessage())));
        response.getWriter().flush();
        response.getWriter().close();
    }
}
