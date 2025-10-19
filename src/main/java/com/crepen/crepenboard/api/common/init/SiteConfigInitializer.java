package com.crepen.crepenboard.api.common.init;

import com.crepen.crepenboard.api.module.config.model.SiteConfigKey;
import com.crepen.crepenboard.api.module.config.model.UpdateSiteConfigVO;
import com.crepen.crepenboard.api.module.config.model.entity.SiteConfigEntity;
import com.crepen.crepenboard.api.module.config.service.SiteConfigService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
@Order(1)
public class SiteConfigInitializer implements ApplicationRunner {

    private final SiteConfigService siteConfigService;

    @Override
    public void run(ApplicationArguments args) {

        if (!siteConfigService.isInitialized()) {
            List<SiteConfigEntity> updateConfigList = new ArrayList<>();

            updateConfigList.addAll(initPasswordConfig());
            updateConfigList.addAll(initAccountConfig());

            updateConfigList.add(SiteConfigEntity.set(SiteConfigKey.SITE_INIT , true));

            siteConfigService.setConfig(updateConfigList);

            log.info("SITE CONFIG INITIALIZATION COMPLETE");
        } else {
            log.info("SITE CONFIG IS ALREADY INITIALIZED");
        }
    }


    public List<SiteConfigEntity> initPasswordConfig(){

        return List.of(
                // 계정 비밀번호 최소 길이
                SiteConfigEntity.set(SiteConfigKey.ACCOUNT_PASSWORD_MINIMUM_LENGTH, 8),
                // 계정 비밀번호 최대 길이
                SiteConfigEntity.set(SiteConfigKey.ACCOUNT_PASSWORD_MAXIMUM_LENGTH, 12),
                // 계정 비밀번호 영어 대문자 필수 여부
                SiteConfigEntity.set(SiteConfigKey.ACCOUNT_PASSWORD_REQUIRED_UPPER_ENG, true),
                // 계정 비밀번호 기호 필수 여부
                SiteConfigEntity.set(SiteConfigKey.ACCOUNT_PASSWORD_REQUIRED_SYMBOL, true),
                // 계정 비밀번호 숫자 필수 여부
                SiteConfigEntity.set(SiteConfigKey.ACCOUNT_PASSWORD_REQUIRED_NUMBER, true),
                // 계정 비밀번호 허용 기호
                SiteConfigEntity.set(SiteConfigKey.ACCOUNT_PASSWORD_ALLOW_SYMBOL, "!\"#$%&'()*+,-./:;<=>?@[₩]^_`{|}~")
        );
    }

    public List<SiteConfigEntity> initAccountConfig(){
        return List.of(
                // 계정 ID 최소 길이
                SiteConfigEntity.set(SiteConfigKey.ACCOUNT_ID_MINIMUM_LENGTH , 8),
                // 계정 ID 최대 길이
                SiteConfigEntity.set(SiteConfigKey.ACCOUNT_ID_MAXIMUM_LENGTH , 16),
                // 계정 ID 허용 기호
                SiteConfigEntity.set(SiteConfigKey.ACCOUNT_ID_ALLOW_SYMBOL , "_-"),
                // 계정 삭제 유예 기간 (Second)
                SiteConfigEntity.set(SiteConfigKey.ACCOUNT_TERMINATE_GRACE_PERIOD_SECOND , 60*60*24*7),
                // 계정 삭제 유예 기간 설정 여부
                SiteConfigEntity.set(SiteConfigKey.ACCOUNT_TERMINATE_ENABLE_GRACE_PERIOD , true)
        );
    }
}
