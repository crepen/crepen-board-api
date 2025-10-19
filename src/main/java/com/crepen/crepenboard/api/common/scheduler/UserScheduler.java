package com.crepen.crepenboard.api.common.scheduler;

import com.crepen.crepenboard.api.module.user.model.UserStatus;
import com.crepen.crepenboard.api.module.user.model.entity.UserEntity;
import com.crepen.crepenboard.api.module.user.model.exception.UserException;
import com.crepen.crepenboard.api.module.user.repository.UserRepository;
import com.crepen.crepenboard.api.module.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserScheduler {

    private final UserService userService;
    private  final UserRepository userRepository;

    @Scheduled(cron = "0 0 10 * * *")
    public void terminateUser()  {

        List<UserEntity> terminateUser = userRepository.findByUserStatusAndTerminateDateLessThanEqual(UserStatus.TERMINATE_FROZEN , OffsetDateTime.now(ZoneOffset.UTC));

        try{
            if(!terminateUser.isEmpty()) {
                for(UserEntity user : terminateUser) {
                    userService.removeUser(user.getUuid());
                    log.info("User {} is removed", user.getUuid());
                }
            }
            else{
                log.info("Terminating user not found");
            }
        }
        catch (Exception ex){
            log.error("Terminate user failed", ex);
        }


    }
}
