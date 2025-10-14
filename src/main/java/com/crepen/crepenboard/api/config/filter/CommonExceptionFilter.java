package com.crepen.crepenboard.api.config.filter;

import com.crepen.crepenboard.api.common.CommonResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class CommonExceptionFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try{
            filterChain.doFilter(request, response);
        }
        catch (Exception ex){
            setErrorResponse(HttpStatus.UNAUTHORIZED.value(), response, ex);
        }
    }


    private void setErrorResponse(int status, HttpServletResponse response, Throwable ex) throws IOException {
        response.setStatus(status);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        CommonResponse res = CommonResponse.error(status , HttpStatus.valueOf(status).toString() , ex.getMessage());
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonMessage = objectMapper.writeValueAsString(res);




        response.getWriter().write(jsonMessage);
    }

}
