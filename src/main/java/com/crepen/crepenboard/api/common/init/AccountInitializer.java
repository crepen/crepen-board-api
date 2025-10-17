package com.crepen.crepenboard.api.common.init;

import com.crepen.crepenboard.api.module.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * 초기 최고 관리자 계정 생성 Initializer
 */
@Component
@RequiredArgsConstructor
public class AccountInitializer implements ApplicationRunner {

    private final UserService userService;


    @Override
    public void run(ApplicationArguments args) throws Exception {
        userService.addAdminUser();
    }
}
