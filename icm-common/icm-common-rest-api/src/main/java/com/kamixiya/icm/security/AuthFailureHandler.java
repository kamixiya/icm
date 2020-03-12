package com.kamixiya.icm.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kamixiya.icm.model.common.CommonErrorDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 处理未成功鉴权的HTTP请求，返回客户端错误信息
 *
 * @author Zhu Jie
 * @date 2020/3/10
 */
@Component
public class AuthFailureHandler implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;

    @Autowired
    public AuthFailureHandler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        String msg;
        if (e instanceof BadCredentialsException) {
            // 其实是密码错误, 账号存在
            msg = "错误的用户名或密码！";
        } else if (e instanceof InsufficientAuthenticationException) {
            msg = "没有足够的权限访问，也许是token失效，请重新登录！";
        } else {
            msg = "未处理的安全错误！";
        }

        httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.setHeader("Content-Type", "application/json; charset=UTF-8");

        httpServletResponse.getWriter().write(objectMapper.writeValueAsString(new CommonErrorDTO(msg, e.getLocalizedMessage())));
    }
}
