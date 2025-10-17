package com.crepen.crepenboard.api.util.jwt.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class JwtGroup {
    private String access;
    private String refresh;
}
