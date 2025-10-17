package com.crepen.crepenboard.api.util.jwt.service;

import com.crepen.crepenboard.api.util.jwt.JwtProvider;
import com.crepen.crepenboard.api.module.user.model.entity.UserEntity;
import com.crepen.crepenboard.api.util.jwt.model.JwtType;
import com.crepen.crepenboard.api.util.jwt.model.JwtGroup;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtService {
    private final JwtProvider jwtProvider;

    public JwtGroup createJwtGroup(UserEntity userEntity) {

        String act = jwtProvider.createToken(
                userEntity,
                JwtType.ACCESS
        );

        String rft = jwtProvider.createToken(
                userEntity,
                JwtType.REFRESH
        );

        return JwtGroup.builder()
                .access(act)
                .refresh(rft)
                .build();
    }
}
