package com.crepen.crepenboard.api.repository;

import com.crepen.crepenboard.api.model.entity.BoardEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<BoardEntity, String> {
}
