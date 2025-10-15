package com.crepen.crepenboard.api.service;

import org.springframework.boot.info.BuildProperties;
import org.springframework.stereotype.Service;

@Service
public class VersionService {

    private final BuildProperties buildProperties;

    // 🔑 BuildProperties 객체를 생성자를 통해 주입받습니다.
    public VersionService(BuildProperties buildProperties) {
        this.buildProperties = buildProperties;
    }

    public String getAppVersionFromGradle() {
        // BuildProperties.getVersion() 메서드를 통해 build.gradle의 version 값을 가져옵니다.
        return buildProperties.getVersion(); // 결과: "1.2.3-SNAPSHOT"
    }
}