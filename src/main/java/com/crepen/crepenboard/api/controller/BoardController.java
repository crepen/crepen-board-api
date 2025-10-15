package com.crepen.crepenboard.api.controller;

import com.crepen.crepenboard.api.common.CommonResponse;
import com.crepen.crepenboard.api.model.dto.request.AddBoardRequestDTO;
import com.crepen.crepenboard.api.model.entity.BoardEntity;
import com.crepen.crepenboard.api.model.vo.JwtUserPayload;
import com.crepen.crepenboard.api.service.BoardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
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
    public ResponseEntity<CommonResponse> addBoard(
            @RequestBody AddBoardRequestDTO reqBody,
            @AuthenticationPrincipal JwtUserPayload tokenUserEntity
    ) {

        UUID boardUUID = UUID.randomUUID();

        BoardEntity createEntity = reqBody.convertEntity(tokenUserEntity.getUuid());

        boardService.addBoard(createEntity);

        return CommonResponse.success(
                createEntity
        ).toResponseEntity();
    }
}
