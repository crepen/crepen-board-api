package com.crepen.crepenboard.api.module.board.repository;

import com.crepen.crepenboard.api.module.board.model.entity.BoardEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<BoardEntity, String> {
}
