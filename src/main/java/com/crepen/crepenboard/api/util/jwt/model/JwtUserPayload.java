package com.crepen.crepenboard.api.util.jwt.model;

import com.crepen.crepenboard.api.module.user.model.entity.UserEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class JwtUserPayload  {
    private UserEntity userData;
    private JwtType type;
    private String uuid;

    public Boolean isFindUserData() {
        return this.userData != null;
    }
}
