package com.crepen.crepenboard.api.module.auth.service;

import com.crepen.crepenboard.api.module.auth.model.exception.AuthException;
import com.crepen.crepenboard.api.module.user.model.exception.UserException;
import com.crepen.crepenboard.api.module.log.service.LogService;
import com.crepen.crepenboard.api.module.user.model.entity.UserEntity;
import com.crepen.crepenboard.api.util.jwt.model.JwtType;
import com.crepen.crepenboard.api.module.log.model.LogCategory;
import com.crepen.crepenboard.api.module.log.model.LogType;
import com.crepen.crepenboard.api.module.user.model.UserStatus;
import com.crepen.crepenboard.api.util.jwt.model.JwtGroup;
import com.crepen.crepenboard.api.util.jwt.model.JwtUserPayload;
import com.crepen.crepenboard.api.util.jwt.service.JwtService;
import com.crepen.crepenboard.api.module.user.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserService userService;
    private final BCryptPasswordEncoder passwordEncoder;
    private  final JwtService jwtService;
    private final LogService logService;


    @Transactional(rollbackOn = Exception.class)
    public Optional<JwtGroup> tryLogin(String id , String password) throws UserException, AuthException {
        Optional<UserEntity> matchUser = userService.getUserByUserId(id);

        if(matchUser.isEmpty()){
            throw UserException.USER_NOT_FOUND;
        }
        else if(!passwordEncoder.matches(password , matchUser.get().getUserPassword())){
            throw AuthException.UNMATCHED_PASSWORD;
        }
        else if(matchUser.get().getUserStatus().equals(UserStatus.BLOCK)){
            throw AuthException.BLOCK_USER;
        }

        logService.addLog(
                LogCategory.AUTH,
                LogType.CREATE_TOKEN,
                matchUser.get().getUuid(),
                "Create Token"
        );

        return Optional.ofNullable(jwtService.createJwtGroup(matchUser.get()));
    }

    @Transactional(rollbackOn = Exception.class)
    public Optional<JwtGroup> refreshToken(JwtUserPayload tokenUserEntity) throws AuthException, UserException {
        if(tokenUserEntity == null){
            throw AuthException.EXPIRE_TOKEN;
        }
        else if(tokenUserEntity.getType() != JwtType.REFRESH){
            throw AuthException.UNMATCHED_TOKEN_TYPE;
        }
        else if(!tokenUserEntity.isFindUserData()){
            throw UserException.USER_NOT_FOUND;
        }

        logService.addLog(
                LogCategory.AUTH,
                LogType.TOKEN_REFRESH,
                tokenUserEntity.getUuid(),
                "Refresh Token"
        );

        return Optional.ofNullable(jwtService.createJwtGroup(tokenUserEntity.getUserData()));
    }
}
