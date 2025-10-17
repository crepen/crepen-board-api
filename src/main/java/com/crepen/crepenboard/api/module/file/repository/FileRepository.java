package com.crepen.crepenboard.api.module.file.repository;

import com.crepen.crepenboard.api.module.file.model.entity.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<FileEntity, String> {

}
