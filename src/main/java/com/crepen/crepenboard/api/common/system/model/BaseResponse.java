package com.crepen.crepenboard.api.common.system.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.ResponseEntity;

import java.io.Serializable;

@Getter
@Setter
@Builder
public class BaseResponse implements Serializable {
    private boolean success;
    private Object data;
    private int statusCode;
    private String message;
    private String errorCode;


    public static BaseResponse success(Object data ) {
        return BaseResponse.builder()
                .success(true)
                .statusCode(200)
                .data(data)
                .build();
    }

    public static BaseResponse success() {
        return success(null);
    }

    public static BaseResponse error(int statusCode, String errorCode , String message) {

        return BaseResponse.builder()
                .success(false)
                .statusCode(statusCode)
                .errorCode(errorCode)
                .message(message)
                .build();
    }

    public ResponseEntity<BaseResponse> toResponseEntity(){
        if(this.isSuccess()){
            return ResponseEntity.ok(this);
        }
        else {
            return ResponseEntity.status(this.statusCode).body(this);
        }
    }

}

