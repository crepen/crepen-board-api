package com.crepen.crepenboard.api.controller;

import com.crepen.crepenboard.api.common.CommonResponse;
import com.crepen.crepenboard.api.common.exception.AuthException;
import com.crepen.crepenboard.api.common.exception.UserException;
import com.crepen.crepenboard.api.model.dto.request.LoginRequestDTO;
import com.crepen.crepenboard.api.model.dto.response.AuthTokenDTO;
import com.crepen.crepenboard.api.model.entity.UserEntity;
import com.crepen.crepenboard.api.model.enums.AuthGrantType;
import com.crepen.crepenboard.api.model.enums.JwtType;
import com.crepen.crepenboard.api.model.enums.UserStatusType;
import com.crepen.crepenboard.api.model.vo.JwtGroup;
import com.crepen.crepenboard.api.model.vo.JwtUserPayload;
import com.crepen.crepenboard.api.service.JwtService;
import com.crepen.crepenboard.api.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Tag(name = "Auth Controller" , description = "Auth Controller")
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;
    private final JwtService jwtService;


    @PostMapping("/token")
    @Operation(summary = "Login User / Refresh Token")
    public ResponseEntity<CommonResponse> controlToken(
            @RequestBody LoginRequestDTO reqBody,
            @AuthenticationPrincipal JwtUserPayload tokenUserEntity
    ) throws Exception {



        if(reqBody.getGrantType() == null){
            throw AuthException.UNMATCHED_GRANT_TYPE;
        }

        Optional<JwtGroup> resJwt = Optional.empty();

        if(reqBody.getGrantType() == AuthGrantType.PASSWORD){
            // LOGIN
            Optional<UserEntity> matchUser = userService.getLoginUser(reqBody.getId() , reqBody.getPassword());

            if(matchUser.isEmpty()){
                throw AuthException.FAILED_LOGIN;
            }
            else if(matchUser.get().getUserStatus() == UserStatusType.BLOCK){
                throw AuthException.BLOCK_USER;
            }


            resJwt = Optional.ofNullable(jwtService.createJwtGroup(matchUser.get()));
        }
        else if (reqBody.getGrantType() == AuthGrantType.REFRESH){
            // REFRESH TOKEN
            if(tokenUserEntity == null){
                throw AuthException.EXPIRE_TOKEN;
            }
            else if(tokenUserEntity.getType() != JwtType.REFRESH){
                throw AuthException.UNMATCHED_TOKEN_TYPE;
            }
            else if(!tokenUserEntity.isFindUserData()){
                throw UserException.USER_NOT_FOUND;
            }

            resJwt = Optional.ofNullable(jwtService.createJwtGroup(tokenUserEntity.getUserData()));



        }



        return CommonResponse.success(
                resJwt.map(jwtGroup ->
                        AuthTokenDTO.builder()
                                .accessToken(jwtGroup.getAccess())
                                .refreshToken(jwtGroup.getRefresh())
                                .build()
                ).orElse(null)
        ).toResponseEntity();


    }

}
