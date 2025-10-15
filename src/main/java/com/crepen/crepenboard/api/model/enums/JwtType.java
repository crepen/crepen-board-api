package com.crepen.crepenboard.api.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@AllArgsConstructor
@Getter
public enum JwtType {
    ACCESS("access-token"),
    REFRESH("refresh-token");

    private String typeName;

    public static JwtType of (final String param){
        return Arrays.stream(values())
                .filter(type -> type.typeName.equals(param))
                .findFirst()
                .orElse(null);
    }
}
