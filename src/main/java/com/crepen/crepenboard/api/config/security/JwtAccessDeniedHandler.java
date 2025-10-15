package com.crepen.crepenboard.api.config.security;

import com.crepen.crepenboard.api.common.CommonResponse;
import com.crepen.crepenboard.api.common.exception.AuthException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, org.springframework.security.access.AccessDeniedException accessDeniedException) throws IOException, ServletException {

        AuthException ex = AuthException.ACCESS_DENIED;

        response.setStatus(ex.getStatusCode());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        CommonResponse res = CommonResponse.error(ex.getStatusCode() , ex.getErrorCode(), ex.getMessage());
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonMessage = objectMapper.writeValueAsString(res);

        response.getWriter().write(jsonMessage);
    }
}