package com.crepen.crepenboard.api.repository;

import com.crepen.crepenboard.api.entity.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<FileEntity, String> {

}
