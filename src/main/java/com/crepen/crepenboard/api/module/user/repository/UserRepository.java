package com.crepen.crepenboard.api.module.user.repository;

import com.crepen.crepenboard.api.module.user.model.UserStatus;
import com.crepen.crepenboard.api.module.user.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, String> {
    Optional<UserEntity> findByUserIdAndUserPassword(String id, String password);

    Optional<UserEntity> findByUuid(String userUUID);

    Optional<UserEntity> findByUserIdOrUserNameOrUserEmail(String userId, String userName, String userEmail);

    Optional<UserEntity> findByUserId(String userId);

    @Query(
            "select u FROM UserEntity u join fetch u.userRoles r where u.uuid = :userUuid"
    )
    Optional<UserEntity> findByUserUuidWithUserRole(@Param("userUuid") String userUuid);


    @Query(
            "select u From UserEntity u join fetch u.userRoles r where u.userId = :userId"
    )
    Optional<UserEntity> findByUserIdWithRoles(@Param("userId") String userId);

    @Query(
            "select u FROM UserEntity u join fetch u.userRoles r"
    )
    List<UserEntity> findAllWithRoles();


    List<UserEntity> findByUserStatusAndTerminateDateLessThanEqual(
            UserStatus userStatus,
            OffsetDateTime terminateDate
    );
}