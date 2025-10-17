package com.crepen.crepenboard.api.common.system.model.exception;

import jakarta.servlet.ServletException;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@Getter
public class ResponseException extends ServletException {
    protected ResponseException(int statusCode, String errorCode, String message, String... messageArgs){
        super(message);
        this.statusCode = statusCode;
        this.errorCode = errorCode;
        this.errorMessageArgs = Arrays.stream(messageArgs).toList();
    }

    private final int statusCode;
    private final String errorCode;
    private final List<String> errorMessageArgs;


    public static ResponseException UNKNOWN_EXCEPTION = new ResponseException(500, "Unknown Error", "error.common.unknown" , "test");
}
