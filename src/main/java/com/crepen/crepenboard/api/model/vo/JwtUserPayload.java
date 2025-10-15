package com.crepen.crepenboard.api.model.vo;

import com.crepen.crepenboard.api.model.entity.UserEntity;
import com.crepen.crepenboard.api.model.enums.JwtType;
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
