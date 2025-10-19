package com.crepen.crepenboard.api.module.user.controller;

import com.crepen.crepenboard.api.common.system.model.BaseResponse;
import com.crepen.crepenboard.api.common.system.model.exception.ResponseException;
import com.crepen.crepenboard.api.module.user.model.dto.GetUserDTO;
import com.crepen.crepenboard.api.module.user.service.UserService;
import com.crepen.crepenboard.api.module.user.model.dto.AddUserRequestDTO;
import com.crepen.crepenboard.api.util.jwt.model.JwtUserPayload;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {


    private final UserService userService;

    @PostMapping("/add")
    public ResponseEntity<BaseResponse> addUser(
            @RequestBody AddUserRequestDTO reqBody
    ) throws ResponseException {
        userService.addUser(reqBody.toAddUserVO());

        return BaseResponse.success().toResponseEntity();
    }

    @GetMapping("")
    public ResponseEntity<BaseResponse> getUser(
            @AuthenticationPrincipal JwtUserPayload tokenUserEntity
    ){
        return BaseResponse.success(
                GetUserDTO.convert(tokenUserEntity.getUserData())
        ).toResponseEntity();
    }

    @GetMapping("{userUuid}")
    public ResponseEntity<BaseResponse> getUserUuid(
            @PathVariable String userUuid
    ){


        return BaseResponse.success().toResponseEntity();
    }




}
