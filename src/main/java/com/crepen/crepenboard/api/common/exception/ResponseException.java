package com.crepen.crepenboard.api.common.exception;

import jakarta.servlet.ServletException;
import lombok.Getter;

@Getter
public class ResponseException extends ServletException {
    ResponseException(int statusCode, String errorCode, String message){
        super(message);
        this.statusCode = statusCode;
        this.errorCode = errorCode;
    }

    private final int statusCode;
    private final String errorCode;
}
