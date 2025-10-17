package com.crepen.crepenboard.api.module.board.model.dto;

import com.crepen.crepenboard.api.module.board.model.entity.BoardEntity;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;

@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class AddBoardRequestDTO {
    private String boardName;
    private String boardDescription;
    private Boolean isPublic;
    private String boardAlias;


    public BoardEntity convertEntity(String ownerUserUUID){

        return BoardEntity.builder()
                .boardName(boardName)
                .boardAlias(boardAlias)
                .boardOwner(ownerUserUUID)
                .boardDesc(boardDescription)
                .isPublic(isPublic)
                .build();
    }
}
