package com.xu.wxxplatformserver.security;

import com.xu.wxxplatformserver.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Description 登录成功
 * Date 2021/11/10 22:42
 * Version 1.0.1
 *
 * @author Wen
 */
@Component
public class MyLoginSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private JwtUtil jwtUtil;

    @Value("${jwt.tokenHeader}")
    private String tokenHeader;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {

        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");

        // 生成jwt，把token设置到请求头中
        String token = jwtUtil.generateToken((UserDetails) authentication.getDetails());
        response.setHeader(tokenHeader, token);


        response.getWriter().flush();
        response.getWriter().close();
    }
}
