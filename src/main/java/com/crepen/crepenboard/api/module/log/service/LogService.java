package com.crepen.crepenboard.api.module.log.service;

import com.crepen.crepenboard.api.module.log.model.entity.LogEntity;
import com.crepen.crepenboard.api.module.log.model.LogCategory;
import com.crepen.crepenboard.api.module.log.model.LogType;
import com.crepen.crepenboard.api.module.log.repository.LogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogService {

    private final LogRepository logRepository;


    public void addLog(LogCategory logCategory , LogType logType , String targetUid , String message){
        logRepository.save(
                LogEntity.builder()
                        .logCategory(logCategory)
                        .logType(logType)
                        .targetUid(targetUid)
                        .message(message)
                        .build()
        );
    }

}
