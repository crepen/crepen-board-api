package com.crepen.crepenboard.api.module.board.controller;

import com.crepen.crepenboard.api.common.system.model.BaseResponse;
import com.crepen.crepenboard.api.module.board.model.dto.AddBoardRequestDTO;
import com.crepen.crepenboard.api.module.board.model.entity.BoardEntity;
import com.crepen.crepenboard.api.util.jwt.model.JwtUserPayload;
import com.crepen.crepenboard.api.module.board.service.BoardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Tag(name = "Board Controller" , description = "Board Control")
@RestController
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {

    private final BoardService boardService;

    @Operation(summary = "Get Board List")
    @GetMapping
    public ResponseEntity<String> getBoardList() {
        return ResponseEntity.ok("BD");
    }


    @PostMapping
    public ResponseEntity<BaseResponse> addBoard(
            @RequestBody AddBoardRequestDTO reqBody,
            @AuthenticationPrincipal JwtUserPayload tokenUserEntity
    ) {

        UUID boardUUID = UUID.randomUUID();

        BoardEntity createEntity = reqBody.convertEntity(tokenUserEntity.getUuid());

        boardService.addBoard(createEntity);

        return BaseResponse.success(
                createEntity
        ).toResponseEntity();
    }
}
