package com.crepen.crepenboard.api.model.dto.request;

import com.crepen.crepenboard.api.model.entity.UserEntity;
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


    public UserEntity convertEntity(){

        return UserEntity.builder()
                .userName(userName)
                .userId(userId)
                .userEmail(userEmail)
                .userPassword(password)
                .build();
    }
}
