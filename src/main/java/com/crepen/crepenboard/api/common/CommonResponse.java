package com.crepen.crepenboard.api.common;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;

import java.io.Serializable;

@Getter
@Setter
@Builder
public class CommonResponse implements Serializable {
    private boolean success;
    private Object data;
    private int statusCode;
    private String message;
    private String errorCode;


    public static CommonResponse success(Object data ) {
        return CommonResponse.builder()
                .success(true)
                .statusCode(200)
                .data(data)
                .build();
    }

    public static CommonResponse success() {
        return success(null);
    }

    public static CommonResponse error(int statusCode,String errorCode ,String message) {

        return CommonResponse.builder()
                .success(false)
                .statusCode(statusCode)
                .errorCode(errorCode)
                .message(message)
                .build();
    }

    public ResponseEntity<CommonResponse> toResponseEntity(){
        if(this.isSuccess()){
            return ResponseEntity.ok(this);
        }
        else {
            return ResponseEntity.status(this.statusCode).body(this);
        }
    }

}

