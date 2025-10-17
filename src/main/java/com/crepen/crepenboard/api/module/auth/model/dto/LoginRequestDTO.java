package com.crepen.crepenboard.api.module.auth.model.dto;

import com.crepen.crepenboard.api.module.auth.model.AuthGrantType;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class LoginRequestDTO {
    @Schema(description = "User ID / Email" , example = "USERID")
    private String id;

    @Schema(description = "User Password" , example = "PASSWORD")
    private String password;

    @Schema(description = "Grant Type" , examples = {"password" , "refresh"})
    private AuthGrantType grantType;
}
