package com.jty.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jty.domain.entity.User;
import com.jty.utils.RedisCache;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

public class JsonLoginFilter extends UsernamePasswordAuthenticationFilter {

    @Resource
    private RedisCache redisCache;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        // 获取请求头，据此判断请求参数类型
        String contentType = request.getContentType();
        if (MediaType.APPLICATION_JSON_VALUE.equalsIgnoreCase(contentType) || MediaType.APPLICATION_JSON_UTF8_VALUE.equalsIgnoreCase(contentType)) {
            // 说明请求参数是 JSON
            if (!request.getMethod().equals("POST")) {
                throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
            }
            String username = null;
            String password = null;
            try {
                // 解析请求体中的 JSON 参数
                User user = new ObjectMapper().readValue(request.getInputStream(), User.class);
                username = user.getUsername();
                username = (username != null) ? username.trim() : "";
                password = user.getPassword();
                password = (password != null) ? password : "";
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            // 构建登录令牌
            UsernamePasswordAuthenticationToken authRequest = UsernamePasswordAuthenticationToken.unauthenticated(username,
                    password);
            // Allow subclasses to set the "details" property
            setDetails(request, authRequest);
            // 执行真正的登录操作
            Authentication auth = this.getAuthenticationManager().authenticate(authRequest);

            return auth;
        } else {
            return super.attemptAuthentication(request, response);
        }
    }
}