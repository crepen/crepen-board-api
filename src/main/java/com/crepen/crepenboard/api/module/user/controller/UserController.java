package com.crepen.crepenboard.api.module.user.controller;

import com.crepen.crepenboard.api.common.system.model.BaseResponse;
import com.crepen.crepenboard.api.common.system.model.exception.ResponseException;
import com.crepen.crepenboard.api.module.user.service.UserService;
import com.crepen.crepenboard.api.module.user.model.dto.AddUserRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("/test")
    public ResponseEntity<BaseResponse> getUser(){
        return BaseResponse.success().toResponseEntity();
    }


}
