package com.crepen.crepenboard.api.module.user.service;

import com.crepen.crepenboard.api.common.system.service.GlobalConfigureService;
import com.crepen.crepenboard.api.module.auth.model.exception.AuthException;
import com.crepen.crepenboard.api.common.system.model.exception.ResponseException;
import com.crepen.crepenboard.api.module.config.model.SiteConfigKey;
import com.crepen.crepenboard.api.module.user.model.UserStatus;
import com.crepen.crepenboard.api.module.user.model.exception.UserException;
import com.crepen.crepenboard.api.module.user.model.AddUserVO;
import com.crepen.crepenboard.api.module.user.model.entity.UserEntity;
import com.crepen.crepenboard.api.module.user.repository.UserRepository;
import com.crepen.crepenboard.api.module.user.repository.UserRoleRepository;
import com.crepen.crepenboard.api.module.user.model.entity.UserRoleEntity;
import com.crepen.crepenboard.api.module.user.model.UserRole;
import com.crepen.crepenboard.api.module.log.model.LogCategory;
import com.crepen.crepenboard.api.module.log.model.LogType;
import com.crepen.crepenboard.api.module.log.service.LogService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final UserValidateService userValidateService;
    private final LogService logService;
    private final GlobalConfigureService globalConfigureService;

    public Optional<UserEntity> getLoginUser(String id , String password) throws AuthException {
        return userRepository.findByUserIdAndUserPassword(id , password);
    }

    public Optional<UserEntity> getUserByUUID(String uuid){
        return userRepository.findByUuid(uuid);
    }

    public List<UserEntity> findAll(){
        return userRepository.findAll();
    }

    public Optional<UserEntity> getUserByUserId(String userId){
        return userRepository.findByUserId(userId);
    }


    public Optional<UserEntity> getUserByUserIdWithRoles(String id ){
        return userRepository.findByUserIdWithRoles(id);
    }

    public Optional<UserEntity> getUserByUserUuidWithRoles(String uuid){
        return userRepository.findByUserUuidWithUserRole(uuid);
    }

    public List<UserEntity> getUsersWithRoles(){
        return userRepository.findAllWithRoles();
    }


    /**
     * 사용자 생성
     *
     * @param user 생성 사용자 VO
     * @throws ResponseException
     */
    @Transactional(rollbackOn = Exception.class)
    public void addUser(AddUserVO user) throws ResponseException {
        addUser(user , new ArrayList<>());
    }

    /**
     * 사용자 생성
     *
     * @param user 생성 사용자 VO
     * @param appendRoles 기본 외 추가 Roles
     * @throws UserException
     */
    @Transactional(rollbackOn = Exception.class)
    public void addUser(AddUserVO user , List<UserRole> appendRoles) throws ResponseException {
        Optional<UserEntity> duplicateUserData = userRepository.findByUserIdOrUserNameOrUserEmail(
                user.getUserId() , user.getUserName() , user.getUserEmail()
        );

        if(duplicateUserData.isPresent()){
            if(Objects.equals(duplicateUserData.get().getUserId(), user.getUserId())){
                throw UserException.ADD_USER_DUPLICATE_ID;
            }
            else if(Objects.equals(duplicateUserData.get().getUserName(), user.getUserName())){
                throw UserException.ADD_USER_DUPLICATE_NAME;
            }
            else if(Objects.equals(duplicateUserData.get().getUserEmail(), user.getUserEmail())){
                throw UserException.ADD_USER_DUPLICATE_EMAIL;
            }
        }


        if(!userValidateService.isValidId(user.getUserId())){
            throw UserException.ADD_USER_INVALID_USERID;
        }
        else if(!userValidateService.isValidEmail(user.getUserEmail())){
            throw UserException.ADD_USER_INVALID_EMAIL;
        }
        else if(!userValidateService.isValidPassword(user.getPassword())){
            throw UserException.ADD_USER_INVALID_PASSWORD;
        }


        user.encryptPassword(passwordEncoder::encode);


        UserEntity saveUserEntity = userRepository.save(user.toUserEntity());

        List<UserRoleEntity> userRoles = new ArrayList<>();
        userRoles.add(
                UserRoleEntity.builder()
                        .user(saveUserEntity)
                        .role(UserRole.ROLE_COMMON_USER.getKey())
                        .build()
        );





        if(!appendRoles.isEmpty()){
            appendRoles.forEach(item ->
                userRoles.add(
                        UserRoleEntity.builder()
                            .user(saveUserEntity)
                                .role(item.getKey())
                                .build()
                )
            );
        }

        userRoleRepository.saveAll(userRoles);



        logService.addLog(
                LogCategory.USER,
                LogType.CREATE_USER,
                saveUserEntity.getUuid(),
                "Create user"
        );


    }

    /**
     * 최고 관리자 생성
     */
    @Transactional(rollbackOn = Exception.class)
    public void addAdminUser() {
        AddUserVO adminAccount = AddUserVO.builder()
                .password("qwer1234" )
                .userEmail("admin@crepen.cloud")
                .userId("administrator")
                .userName("Admin")
                .build();


        Optional<UserEntity> duplicateUserData = userRepository.findByUserId(adminAccount.getUserId());



        if(duplicateUserData.isEmpty()){

            adminAccount.encryptPassword(passwordEncoder::encode);

            UserEntity saveUserEntity = userRepository.save(
                    adminAccount.toUserEntity()
            );

            userRoleRepository.save(
                    UserRoleEntity.builder()
                            .user(saveUserEntity)
                            .role(UserRole.ROLE_SUDO.getKey())
                            .role(UserRole.ROLE_ADMIN.getKey())
                            .build()
            );
        }
    }


    /**
     * 사용자 차단
     *
     * @param userUuid 차단 사용자 UUID
     */
    @Transactional(rollbackOn = Exception.class)
    public void blockUser(String userUuid) throws UserException {
        Optional<UserEntity> matchUser = userRepository.findByUuid(userUuid);

        if(matchUser.isEmpty()){
            throw UserException.USER_NOT_FOUND;
        }
        matchUser.get().setUserStatus(UserStatus.BLOCK);

        userRepository.save(matchUser.get());
    }

    /**
     * 사용자 삭제 예약
     *
     * @param userUuid 삭제 사용자 UUID
     */
    @Transactional(rollbackOn = Exception.class)
    public void terminateUser(String userUuid) throws UserException {
        int userTerminateSecond = globalConfigureService.get(
                SiteConfigKey.ACCOUNT_TERMINATE_GRACE_PERIOD_SECOND.name() ,
                60*60*24*7 ,
                Integer.class
        );

        boolean isActiveTerminateGracePeriod = globalConfigureService.get(
                SiteConfigKey.ACCOUNT_TERMINATE_ENABLE_GRACE_PERIOD.name(),
                true,
                Boolean.class
        );

        if(!isActiveTerminateGracePeriod){
            removeUser(userUuid);
        }
        else{
            Optional<UserEntity> matchUser = userRepository.findByUuid(userUuid);

            if(matchUser.isEmpty()){
                throw UserException.USER_NOT_FOUND;
            }
            matchUser.get().setUserStatus(UserStatus.TERMINATE_FROZEN);
            matchUser.get().setTerminateDate(OffsetDateTime.now(ZoneOffset.UTC).plusSeconds(userTerminateSecond));

            userRepository.save(matchUser.get());
        }


    }


    /**
     * 사용자 삭제
     *
     * @param userUuid 삭제 사용자 UUID
     * @apiNote 실제 DB에서 삭제하는 로직
     */
    @Transactional(rollbackOn = Exception.class)
    public void removeUser(String userUuid) throws UserException {
        boolean isActiveTerminateGracePeriod = globalConfigureService.get(
                SiteConfigKey.ACCOUNT_TERMINATE_ENABLE_GRACE_PERIOD.name(),
                true,
                Boolean.class
        );

        Optional<UserEntity> matchUser = userRepository.findByUuid(userUuid);

        if(matchUser.isEmpty()){
            throw UserException.USER_NOT_FOUND;
        }


        if(isActiveTerminateGracePeriod ){
            if( matchUser.get().getTerminateDate() == null || OffsetDateTime.now(ZoneOffset.UTC).isBefore(matchUser.get().getTerminateDate())){
                throw UserException.TERMINATE_USER_NOT_EXPIRED_GRACE_PERIOD;
            }
        }

        // 완전 삭제
        // userRepository.delete(matchUser.get());

        // 개인 정보만 삭제
        matchUser.get().setUserRoles(new ArrayList<>());
        matchUser.get().setUserEmail("-");
        matchUser.get().setUserId("-");
        matchUser.get().setUserName("-");
        matchUser.get().setUserPassword("-");
        matchUser.get().setUserStatus(UserStatus.DELETE);



        userRepository.save(matchUser.get());
//        userRoleRepository.deleteAllByUser(matchUser.get());
    }

}
