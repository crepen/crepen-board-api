package com.crepen.crepenboard.api.common.init;

import com.crepen.crepenboard.api.common.system.model.GlobalConfigVO;
import com.crepen.crepenboard.api.common.system.service.GlobalConfigureService;
import com.crepen.crepenboard.api.module.admin.config.model.entity.SiteConfigEntity;
import com.crepen.crepenboard.api.module.admin.config.service.SiteConfigService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 사이트 설정값 메모리 로드
 */
@Component
@RequiredArgsConstructor
@Order(2)
public class LoadGlobalConfigInitializer implements ApplicationRunner {

    private final SiteConfigService siteConfigService;
    private final GlobalConfigureService globalConfigureService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        List<SiteConfigEntity> propList = siteConfigService.getAllProperties();

        List<GlobalConfigVO> globalConfigList = new ArrayList<>();

        propList.forEach(item -> {
            globalConfigList.add(
                    GlobalConfigVO.builder()
                            .configKey(item.getConfigKey().toUpperCase())
                            .configValue(item.getConfigValue())
                            .configType(item.getConfigType())
                            .build()
            );
        });

        globalConfigureService.setAll(globalConfigList);
    }
}
