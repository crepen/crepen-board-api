package com.crepen.crepenboard.api.model.dto.response;

import com.crepen.crepenboard.api.model.vo.JwtGroup;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class AuthTokenDTO {
    private String accessToken;
    private String refreshToken;
}
