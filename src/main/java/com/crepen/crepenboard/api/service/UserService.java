package com.crepen.crepenboard.api.service;

import com.crepen.crepenboard.api.common.exception.AuthException;
import com.crepen.crepenboard.api.common.exception.UserException;
import com.crepen.crepenboard.api.model.entity.UserEntity;
import com.crepen.crepenboard.api.model.enums.UserStatusType;
import com.crepen.crepenboard.api.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public Optional<UserEntity> getLoginUser(String id , String password) throws AuthException {
        return userRepository.findByUserIdAndUserPassword(id , password);
    }

    public Optional<UserEntity> getUserByUUID(String uuid){
        return userRepository.findByUuid(uuid);
    }

    public List<UserEntity> findAll(){
        return userRepository.findAll();
    }

    @Transactional(rollbackOn = Exception.class)
    public void addUser(UserEntity user) throws UserException {
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
    }
}
