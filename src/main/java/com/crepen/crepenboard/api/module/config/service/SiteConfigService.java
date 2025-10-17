package com.crepen.crepenboard.api.module.config.service;

import com.crepen.crepenboard.api.module.config.model.entity.SiteConfigEntity;
import com.crepen.crepenboard.api.module.config.model.SiteConfigKey;
import com.crepen.crepenboard.api.module.config.model.UpdateSiteConfigVO;
import com.crepen.crepenboard.api.module.config.repository.SiteConfigRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SiteConfigService {

    private final SiteConfigRepository siteConfigRepository;

    public boolean isInitialized(){
        Optional<SiteConfigEntity> configInitState = siteConfigRepository.findByConfigKey(SiteConfigKey.SITE_INIT);
        return configInitState.isPresent() && Boolean.parseBoolean(configInitState.get().getConfigValue());
    }


    @Transactional(rollbackOn = Exception.class)
    public void setConfig(UpdateSiteConfigVO configVO){

        List<SiteConfigKey> updatekeyList = new ArrayList<>(configVO.getUpdateList().keySet());

        List<SiteConfigEntity> updateEntityList = new ArrayList<>();

        updatekeyList.forEach(item -> {
            updateEntityList.add(
                    SiteConfigEntity.builder()
                            .configKey(item)
                            .configValue(configVO.getUpdateList().get(item))
                            .build()
            );
        });

        siteConfigRepository.saveAll(updateEntityList);
    }


    public List<SiteConfigEntity> getPasswordConfig(){

        return siteConfigRepository.findAllByConfigKeyIn(
              List.of(
                      SiteConfigKey.PASSWORD_MAXIMUM_LENGTH,
                      SiteConfigKey.PASSWORD_MINIMUM_LENGTH,
                      SiteConfigKey.PASSWORD_REQUIRED_NUMBER,
                      SiteConfigKey.PASSWORD_REQUIRED_SYMBOL,
                      SiteConfigKey.PASSWORD_REQUIRED_UPPER_ENG,
                      SiteConfigKey.PASSWORD_ALLOW_SYMBOL
              )
        );
    }
}
