package com.crepen.crepenboard.api.module.user.model.dto;

import com.crepen.crepenboard.api.module.user.model.entity.UserEntity;
import com.crepen.crepenboard.api.module.user.model.entity.UserRoleEntity;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class GetUserDTO {

    String uuid;
    String name;
    String userId;
    String email;
    String status;
    List<String> roles;

    public static GetUserDTO convert(UserEntity userEntity) {
        return GetUserDTO.builder()
                .uuid(userEntity.getUuid())
                .status(userEntity.getUserStatus().name())
                .userId(userEntity.getUserId())
                .name(userEntity.getUserName())
                .email(userEntity.getUserEmail())
                .roles(userEntity.getUserRoles().stream().map(UserRoleEntity::getRole).toList())
                .build();
    }
}
