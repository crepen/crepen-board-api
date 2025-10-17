package com.crepen.crepenboard.api.module.user.repository;

import com.crepen.crepenboard.api.module.user.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, String> {
    Optional<UserEntity> findByUserIdAndUserPassword(String id , String password);
    Optional<UserEntity> findByUuid(String userUUID);
    Optional<UserEntity> findByUserIdOrUserNameOrUserEmail(String userId, String userName , String userEmail);
    Optional<UserEntity> findByUserId(String userId);
}
