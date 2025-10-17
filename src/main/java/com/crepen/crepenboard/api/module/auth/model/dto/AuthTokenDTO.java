package com.crepen.crepenboard.api.module.auth.model.dto;

import com.crepen.crepenboard.api.util.jwt.model.JwtGroup;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

import java.util.Optional;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class AuthTokenDTO {
    private String accessToken;
    private String refreshToken;

    public static Optional<AuthTokenDTO> appendJwtGroup(Optional<JwtGroup> resJwt){
        return resJwt.map(jwtGroup ->
                AuthTokenDTO.builder()
                        .accessToken(jwtGroup.getAccess())
                        .refreshToken(jwtGroup.getRefresh())
                        .build()
        );
    }
}
