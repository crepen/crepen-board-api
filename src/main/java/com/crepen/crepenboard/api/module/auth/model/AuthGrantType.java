package com.crepen.crepenboard.api.module.auth.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum AuthGrantType {
    PASSWORD("password"),
    REFRESH("refresh");

    private String typeName;


    AuthGrantType(final String type){
        this.typeName = type;
    }

    @JsonCreator
    public static AuthGrantType of(final String parameter){
        return Arrays.stream(values())
                .filter(type -> type.typeName.equals(parameter))
                .findFirst()
                .orElse(null);
//                .orElseThrow(() -> new IllegalArgumentException("유형이 잘못되었습니다."));
    }
}
