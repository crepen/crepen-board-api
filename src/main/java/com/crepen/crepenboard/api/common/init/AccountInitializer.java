package com.crepen.crepenboard.api.common.init;

import com.crepen.crepenboard.api.module.user.model.AddUserVO;
import com.crepen.crepenboard.api.module.user.model.UserRole;
import com.crepen.crepenboard.api.module.user.model.entity.UserEntity;
import com.crepen.crepenboard.api.module.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * 초기 최고 관리자 / 데모 계정 생성 Initializer
 */
@Component
@RequiredArgsConstructor
@Order(3)
public class AccountInitializer implements ApplicationRunner {

    private final UserService userService;


    @Override
    public void run(ApplicationArguments args) throws Exception {
        // Init Admin User
        userService.addAdminUser();

        Optional<UserEntity> demoUser = userService.getUserByUserId("demouser");

        if (demoUser.isEmpty()) {
            // Init Demo User
            userService.addUser(
                    AddUserVO.builder()
                            .userId("demouser")
                            .userEmail("demo@board.crepen.cloud")
                            .userName("Demo User")
                            .password("Qwer1234!@")
                            .build(),
                    List.of(UserRole.ROLE_DEMO)
            );
        }
    }
}
