package com.crepen.crepenboard.api.common.filter;

import com.crepen.crepenboard.api.common.system.model.BaseResponse;
import com.crepen.crepenboard.api.common.system.model.exception.ResponseException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Locale;

@Component
@RequiredArgsConstructor
public class CommonExceptionFilter extends OncePerRequestFilter {

    private final MessageSource messageSource;

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
            System.err.println(ex.getMessage());
            if(ex instanceof ResponseException rex){
                errorResponse(rex , request , response);
            }
            else{
                ResponseException rex = ResponseException.UNKNOWN_EXCEPTION;
                errorResponse(rex , request , response);
            }
        }
        catch (Exception ex){
            System.err.println(ex.getMessage());
            ResponseException rex = ResponseException.UNKNOWN_EXCEPTION;
            errorResponse(rex , request , response);
        }
    }

    private void errorResponse(ResponseException rex , HttpServletRequest request , HttpServletResponse response ) throws IOException {

        Locale requestLocale = request.getLocale();

        response.setStatus(rex.getStatusCode());
        response.setContentType("application/json;charset=UTF-8");


        String message = messageSource.getMessage(rex.getMessage(), rex.getErrorMessageArgs().toArray(), requestLocale);


        BaseResponse res = BaseResponse.error(
                rex.getStatusCode() ,
                rex.getErrorCode() ,
                message
//                rex.getMessage()
        );
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonMessage = objectMapper.writeValueAsString(res);

        response.getWriter().write(jsonMessage);
    }

}
