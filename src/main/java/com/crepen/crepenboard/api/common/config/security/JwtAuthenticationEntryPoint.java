package com.crepen.crepenboard.api.common.config.security;

import com.crepen.crepenboard.api.common.system.model.BaseResponse;
import com.crepen.crepenboard.api.module.auth.model.exception.AuthException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException {

        AuthException ex = AuthException.UNAUTHORIZED;

        response.setStatus(ex.getStatusCode());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        BaseResponse res = BaseResponse.error(ex.getStatusCode() , ex.getErrorCode(), ex.getMessage());
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonMessage = objectMapper.writeValueAsString(res);

        response.getWriter().write(jsonMessage);
    }
}