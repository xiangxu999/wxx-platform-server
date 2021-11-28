package com.xu.wxxplatformserver.security;

import cn.hutool.json.JSONUtil;
import com.xu.wxxplatformserver.common.Result;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Description 未登录或者token失效访问接口时，自定义的返回结果
 * Date 2021/11/9 17:03
 * Version 1.0.1
 *
 * @author Wen
 */
@Component
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().println(JSONUtil.parse(Result.unauthorized(e.getMessage())));
        response.getWriter().flush();
        response.getWriter().close();
    }
}
