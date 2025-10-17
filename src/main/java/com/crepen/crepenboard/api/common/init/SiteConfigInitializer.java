package com.crepen.crepenboard.api.common.init;

import com.crepen.crepenboard.api.module.config.model.SiteConfigKey;
import com.crepen.crepenboard.api.module.config.model.UpdateSiteConfigVO;
import com.crepen.crepenboard.api.module.config.service.SiteConfigService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class SiteConfigInitializer implements ApplicationRunner {

    private final SiteConfigService siteConfigService;

    @Override
    public void run(ApplicationArguments args) {

        if (!siteConfigService.isInitialized()) {
            UpdateSiteConfigVO updateSiteConfigVO = UpdateSiteConfigVO.builder().build();

            updateSiteConfigVO.append(SiteConfigKey.PASSWORD_MAXIMUM_LENGTH, String.valueOf(12));
            updateSiteConfigVO.append(SiteConfigKey.PASSWORD_MINIMUM_LENGTH, String.valueOf(8));
            updateSiteConfigVO.append(SiteConfigKey.PASSWORD_REQUIRED_UPPER_ENG, String.valueOf(true));
            updateSiteConfigVO.append(SiteConfigKey.PASSWORD_REQUIRED_SYMBOL, String.valueOf(true));
            updateSiteConfigVO.append(SiteConfigKey.PASSWORD_REQUIRED_NUMBER, String.valueOf(true));
            updateSiteConfigVO.append(SiteConfigKey.PASSWORD_ALLOW_SYMBOL , "!\"#$%&'()*+,-./:;<=>?@[₩]^_`{|}~");
            updateSiteConfigVO.append(SiteConfigKey.SITE_INIT, String.valueOf(true));

            siteConfigService.setConfig(updateSiteConfigVO);

            log.info("SITE CONFIG INITIALIZATION COMPLETE");
        } else {
            log.info("SITE CONFIG IS ALREADY INITIALIZED");
        }
    }
}
