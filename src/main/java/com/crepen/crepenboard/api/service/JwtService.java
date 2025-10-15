package com.crepen.crepenboard.api.service;

import com.crepen.crepenboard.api.common.provider.JwtProvider;
import com.crepen.crepenboard.api.model.entity.UserEntity;
import com.crepen.crepenboard.api.model.enums.JwtType;
import com.crepen.crepenboard.api.model.vo.JwtGroup;
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
