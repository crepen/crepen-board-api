package com.crepen.crepenboard.api.module.user.model.dto;

import com.crepen.crepenboard.api.module.user.model.AddUserVO;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;

@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class AddUserRequestDTO {
    private String userName;
    private String password;
    private String userId;
    private String userEmail;

    public AddUserVO toAddUserVO() {
        return AddUserVO.builder()
                .userName(userName)
                .password(password)
                .userId(userId)
                .userEmail(userEmail)
                .build();
    }
}
