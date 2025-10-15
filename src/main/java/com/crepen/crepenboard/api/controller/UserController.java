package com.crepen.crepenboard.api.controller;

import com.crepen.crepenboard.api.common.CommonResponse;
import com.crepen.crepenboard.api.model.dto.request.AddUserRequestDTO;
import com.crepen.crepenboard.api.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {


    private final UserService userService;

    @PostMapping("/add")
    public ResponseEntity<CommonResponse> addUser(
            @RequestBody AddUserRequestDTO reqBody
    ) {
        return CommonResponse.success().toResponseEntity();
    }

    @GetMapping("/test")
    public ResponseEntity<CommonResponse> getUser(){
        return CommonResponse.success().toResponseEntity();
    }


}
