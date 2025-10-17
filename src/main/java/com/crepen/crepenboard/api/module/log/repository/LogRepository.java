package com.crepen.crepenboard.api.module.log.repository;

import com.crepen.crepenboard.api.module.log.model.entity.LogEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LogRepository extends JpaRepository<LogEntity , Long> {

}
