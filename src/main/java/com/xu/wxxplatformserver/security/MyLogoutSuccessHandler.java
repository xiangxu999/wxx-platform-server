package com.xu.wxxplatformserver.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;

import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * Description 退出
 * Date 2021/11/10 22:51
 * Version 1.0.1
 *
 * @author Wen
 */
@Component
public class MyLogoutSuccessHandler implements LogoutSuccessHandler {

    @Value("${jwt.tokenHeader}")
    private String tokenHeader;

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {

        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");

        // 返回的请求体头部置为空
        response.setHeader(tokenHeader, "");


        response.getWriter().flush();
        response.getWriter().close();
    }
}
