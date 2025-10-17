package com.crepen.crepenboard.api.module.user.repository;

import com.crepen.crepenboard.api.module.user.model.entity.UserRoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRoleRepository extends JpaRepository<UserRoleEntity , Long> {
}
