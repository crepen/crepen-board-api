package com.crepen.crepenboard.api.module.user.service;

import com.crepen.crepenboard.api.module.auth.model.exception.AuthException;
import com.crepen.crepenboard.api.common.system.model.exception.ResponseException;
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
    /**
     * 사용자 생성
     *
     * @param user
     * @throws UserException
     */
    @Transactional(rollbackOn = Exception.class)
    public void addUser(AddUserVO user) throws ResponseException {
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
        userRoleRepository.save(
                UserRoleEntity.builder()
                        .userUuid(saveUserEntity.getUuid())
                        .role(UserRole.ROLE_COMMON_USER.getKey())
                        .build()
        );



        logService.addLog(
                LogCategory.USER,
                LogType.CREATE_USER,
                saveUserEntity.getUuid(),
                "Create user"
        );


    }

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
                            .userUuid(saveUserEntity.getUuid())
                            .role(UserRole.ROLE_SUDO.getKey())
                            .build()
            );
        }
    }




}
