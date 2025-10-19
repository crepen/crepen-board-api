package com.crepen.crepenboard.api.common.init;

import com.crepen.crepenboard.api.common.system.service.GlobalConfigureService;
import com.crepen.crepenboard.api.module.config.model.entity.SiteConfigEntity;
import com.crepen.crepenboard.api.module.config.service.SiteConfigService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

@Component
@RequiredArgsConstructor
@Order(2)
public class LoadGlobalConfigInitializer implements ApplicationRunner {

    private final SiteConfigService siteConfigService;
    private final GlobalConfigureService globalConfigureService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        List<SiteConfigEntity> propList = siteConfigService.getAllProperties();

        Map<String , String> propMap = new ConcurrentHashMap<>();

        propList.forEach(item -> {
            propMap.put(item.getConfigKey().name() , item.getConfigValue());
        });

        globalConfigureService.setAll(propMap);
    }
}
