package com.crepen.crepenboard.api.common.filter;

import com.crepen.crepenboard.api.common.CommonResponse;
import com.crepen.crepenboard.api.common.exception.ResponseException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.hibernate.cfg.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class CommonExceptionFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws IOException {
        try{
            filterChain.doFilter(request, response);
        }
        catch (ServletException ex){
            if(ex instanceof ResponseException rex){
                setErrorResponse(rex.getStatusCode() , response , rex.getErrorCode() , rex.getMessage());
            }
            else{
                setErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), response, "UNKNOWN", ex.getMessage());
            }
        }
        catch (Exception ex){
            setErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), response, "UNKNOWN", ex.getMessage());
        }
    }


    private void setErrorResponse(int status, HttpServletResponse response,String errorCode ,String message) throws IOException {
        response.setStatus(status);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        CommonResponse res = CommonResponse.error(status , errorCode , message);
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonMessage = objectMapper.writeValueAsString(res);

        response.getWriter().write(jsonMessage);
    }

}
