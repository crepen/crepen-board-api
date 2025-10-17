package com.crepen.crepenboard.api.module.auth.controller;

import com.crepen.crepenboard.api.common.system.model.BaseResponse;
import com.crepen.crepenboard.api.module.auth.model.exception.AuthException;
import com.crepen.crepenboard.api.module.auth.model.dto.LoginRequestDTO;
import com.crepen.crepenboard.api.module.auth.model.dto.AuthTokenDTO;
import com.crepen.crepenboard.api.module.auth.model.AuthGrantType;
import com.crepen.crepenboard.api.module.auth.service.AuthService;
import com.crepen.crepenboard.api.util.jwt.model.JwtGroup;
import com.crepen.crepenboard.api.util.jwt.model.JwtUserPayload;
import com.crepen.crepenboard.api.util.jwt.service.JwtService;
import com.crepen.crepenboard.api.module.user.service.UserService;
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
    private  final AuthService authService;


    @PostMapping("/token")
    @Operation(summary = "Login User / Refresh Token")
    public ResponseEntity<BaseResponse> controlToken(
            @RequestBody LoginRequestDTO reqBody,
            @AuthenticationPrincipal JwtUserPayload tokenUserEntity
    ) throws Exception {



        if(reqBody.getGrantType() == null){
            throw AuthException.UNMATCHED_GRANT_TYPE;
        }

        Optional<JwtGroup> resJwt;

        if(reqBody.getGrantType() == AuthGrantType.PASSWORD){
            resJwt = authService.tryLogin(reqBody.getId() , reqBody.getPassword());
        }
        else if (reqBody.getGrantType() == AuthGrantType.REFRESH){
            resJwt = authService.refreshToken(tokenUserEntity);
        }
        else{
            throw AuthException.UNMATCHED_GRANT_TYPE;
        }



        return BaseResponse
                .success(
                        AuthTokenDTO.appendJwtGroup(resJwt)
                )
                .toResponseEntity();


    }

}
