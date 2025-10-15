package com.crepen.crepenboard.api.service;

import com.crepen.crepenboard.api.model.entity.BoardEntity;
import com.crepen.crepenboard.api.model.entity.BoardRoleEntity;
import com.crepen.crepenboard.api.model.entity.UserEntity;
import com.crepen.crepenboard.api.repository.BoardRepository;
import com.crepen.crepenboard.api.repository.BoardRoleRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final BoardRoleRepository boardRoleRepository;

    public List<BoardEntity> findAll(){
        return boardRepository.findAll();
    }

    @Transactional(rollbackOn = Exception.class)
    public void addBoard(BoardEntity boardEntity){
        boardRepository.save(boardEntity);

        boardRoleRepository.save(
                BoardRoleEntity.builder()
                        .boardUuid(boardEntity.getUuid())
                        .userUuid(boardEntity.getBoardOwner())
                        .build()
        );
    }
}
