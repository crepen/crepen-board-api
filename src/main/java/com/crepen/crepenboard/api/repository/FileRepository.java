package com.crepen.crepenboard.api.repository;

import com.crepen.crepenboard.api.model.entity.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<FileEntity, String> {

}
