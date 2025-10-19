package com.crepen.crepenboard.api.module.admin.user;

import com.crepen.crepenboard.api.common.system.model.BaseResponse;
import com.crepen.crepenboard.api.module.user.model.dto.GetUserDTO;
import com.crepen.crepenboard.api.module.user.model.entity.UserEntity;
import com.crepen.crepenboard.api.module.user.model.exception.UserException;
import com.crepen.crepenboard.api.module.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/user")
public class AdminUserController {

    private final UserService userService;

    @GetMapping("{userUuid}")
    public ResponseEntity<BaseResponse> getUser(
            @PathVariable("userUuid") String userUuid
    ) throws UserException {
            Optional<UserEntity> matchUser = userService.getUserByUserUuidWithRoles(userUuid);

            if(matchUser.isEmpty()){
                throw UserException.USER_NOT_FOUND;
            }

            return BaseResponse.success(
                    GetUserDTO.convert(matchUser.get())
            ).toResponseEntity();
    }

    @GetMapping()
    public ResponseEntity<BaseResponse> getUsers() {
        List<UserEntity> findUsers = userService.getUsersWithRoles();

        return BaseResponse.success(
                findUsers.stream().map(GetUserDTO::convert).toList()
        ).toResponseEntity();
    }


    /**
     * 사용자 차단
     * @param userUuid 차단 사용자 UUID
     */
    @DeleteMapping("{userUuid}")
    public ResponseEntity<BaseResponse> blockUser(
            @PathVariable String userUuid
    ) throws UserException {
        userService.blockUser(userUuid);
        return BaseResponse.success().toResponseEntity();
    }


    /**
     * 사용자 삭제 예약
     * @apiNote 삭제 등록 시 현재부터 ACCOUNT_TERMINATE_GRACE_PERIOD_SECOND 이후 DB에서 완전 삭제
     * @param userUuid 삭제 사용자 UUID
     */
    @DeleteMapping("{userUuid}/terminate")
    public ResponseEntity<BaseResponse> terminateUser(
            @PathVariable String userUuid
    ) throws UserException {
        userService.terminateUser(userUuid);
        return BaseResponse.success().toResponseEntity();
    }

}
