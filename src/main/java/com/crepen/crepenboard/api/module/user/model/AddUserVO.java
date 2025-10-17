package com.crepen.crepenboard.api.module.user.model;


import com.crepen.crepenboard.api.module.user.model.entity.UserEntity;
import lombok.Builder;
import lombok.Getter;

import java.util.function.Function;

@Getter
@Builder
public class AddUserVO {
    private String userName;
    private String password;
    private String userEmail;
    private String userId;

    private String encryptPassword = "";
    private Boolean isEncryptPassword = false;

    public UserEntity toUserEntity() {

        return UserEntity.builder()
                .userName(userName)
                .userEmail(userEmail)
                .userId(userId)
                .userPassword(isEncryptPassword ? encryptPassword : password)
                .userStatus(UserStatus.STABLE)
                .build();
    }

    public void encryptPassword(Function<String , String> encryptFunction){
        encryptPassword = encryptFunction.apply(password);
        isEncryptPassword = true;
    }
}
