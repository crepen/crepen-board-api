package com.crepen.crepenboard.api.common;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class CommonResponse implements Serializable {
    private boolean success;
    private Object data;
    private int statusCode;
    private String message;
    private String errorCode;


    public static CommonResponse success(Object data) {
        CommonResponse res = new CommonResponse();
        res.setSuccess(true);
        res.setData(data);
        res.setStatusCode(200);
        return res;
    }

    public static CommonResponse error(int statusCode,String errorCode ,String message) {
        CommonResponse res = new CommonResponse();
        res.setSuccess(false);
        res.setStatusCode(statusCode);
        res.setErrorCode(errorCode);
        res.setMessage(message);
        return res;
    }

}

