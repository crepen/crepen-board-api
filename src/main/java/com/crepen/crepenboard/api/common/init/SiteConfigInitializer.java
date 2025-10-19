package com.crepen.crepenboard.api.common.init;

import com.crepen.crepenboard.api.module.admin.config.model.SiteConfigKey;
import com.crepen.crepenboard.api.module.admin.config.model.SiteConfigType;
import com.crepen.crepenboard.api.module.admin.config.model.entity.SiteConfigEntity;
import com.crepen.crepenboard.api.module.admin.config.model.exception.SiteConfigException;
import com.crepen.crepenboard.api.module.admin.config.model.vo.SiteConfigVO;
import com.crepen.crepenboard.api.module.admin.config.service.SiteConfigService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 기본 사이트 설정값 입력
 */
@Slf4j
@Component
@RequiredArgsConstructor
@Order(1)
public class SiteConfigInitializer implements ApplicationRunner {

    private final SiteConfigService siteConfigService;

    @Override
    public void run(ApplicationArguments args) throws SiteConfigException {

        if (!siteConfigService.isInitialized()) {
            List<SiteConfigVO> updateConfigList = new ArrayList<>();

            updateConfigList.addAll(initPasswordConfig());
            updateConfigList.addAll(initAccountConfig());

            updateConfigList.add(
                    SiteConfigVO.builder()
                            .key(SiteConfigKey.SITE_INIT.name())
//                            .value(true)
                            .defaultValue(true)
                            .type(SiteConfigType.STRING.name())
                            .isEditable(false)
                            .build()
            );

            siteConfigService.addConfigObject(updateConfigList , true);

            log.info("SITE CONFIG INITIALIZATION COMPLETE");
        } else {
            log.info("SITE CONFIG IS ALREADY INITIALIZED");
        }
    }


    public List<SiteConfigVO> initPasswordConfig(){

        return List.of(
                // 계정 비밀번호 최소 길이
                SiteConfigVO.builder()
                        .key(SiteConfigKey.ACCOUNT_PASSWORD_MINIMUM_LENGTH.name())
                        .defaultValue(8)
                        .type(SiteConfigType.STRING.name())
                        .build(),
                // 계정 비밀번호 최대 길이
                SiteConfigVO.builder()
                        .key(SiteConfigKey.ACCOUNT_PASSWORD_MAXIMUM_LENGTH.name())
                        .defaultValue(12)
                        .type(SiteConfigType.NUMBER.name())
                        .build(),
                // 계정 비밀번호 영어 대문자 필수 여부
                SiteConfigVO.builder()
                        .key(SiteConfigKey.ACCOUNT_PASSWORD_REQUIRED_UPPER_ENG.name())
                        .defaultValue(true)
                        .type(SiteConfigType.BOOLEAN.name())
                        .build(),
                // 계정 비밀번호 기호 필수 여부
                SiteConfigVO.builder()
                        .key(SiteConfigKey.ACCOUNT_PASSWORD_REQUIRED_SYMBOL.name())
                        .defaultValue(true)
                        .type(SiteConfigType.BOOLEAN.name())
                        .build(),

                // 계정 비밀번호 숫자 필수 여부
                SiteConfigVO.builder()
                        .key(SiteConfigKey.ACCOUNT_PASSWORD_REQUIRED_NUMBER.name())
                        .defaultValue(true)
                        .type(SiteConfigType.BOOLEAN.name())
                        .build(),

                // 계정 비밀번호 허용 기호
                SiteConfigVO.builder()
                        .key(SiteConfigKey.ACCOUNT_PASSWORD_ALLOW_SYMBOL.name())
                        .defaultValue("!\"#$%&'()*+,-./:;<=>?@[₩]^_`{|}~")
                        .type(SiteConfigType.STRING.name())
                        .build()
        );
    }

    public List<SiteConfigVO> initAccountConfig(){
        return List.of(

                // 계정 ID 최소 길이
                SiteConfigVO.builder()
                        .key(SiteConfigKey.ACCOUNT_ID_MINIMUM_LENGTH.name())
                        .defaultValue(8)
                        .type(SiteConfigType.NUMBER.name())
                        .build(),
                // 계정 ID 최대 길이
                SiteConfigVO.builder()
                        .key(SiteConfigKey.ACCOUNT_ID_MAXIMUM_LENGTH.name())
                        .defaultValue(16)
                        .type(SiteConfigType.NUMBER.name())
                        .build(),

                // 계정 ID 허용 기호
                SiteConfigVO.builder()
                        .key(SiteConfigKey.ACCOUNT_ID_ALLOW_SYMBOL.name())
                        .defaultValue("_-")
                        .type(SiteConfigType.STRING.name())
                        .build(),

                // 계정 삭제 유예 기간 (Second)
                SiteConfigVO.builder()
                        .key(SiteConfigKey.ACCOUNT_TERMINATE_GRACE_PERIOD_SECOND.name())
                        .defaultValue(60*60*24*7)
                        .type(SiteConfigType.NUMBER.name())
                        .build(),

                // 계정 삭제 유예 기간 설정 여부
                SiteConfigVO.builder()
                        .key(SiteConfigKey.ACCOUNT_TERMINATE_ENABLE_GRACE_PERIOD.name())
                        .defaultValue(true)
                        .type(SiteConfigType.BOOLEAN.name())
                        .build()
        );
    }
}
