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

    public List<SiteConfigEntity> getAllProperties(){
        return siteConfigRepository.findAll();
    }

    @Transactional(rollbackOn = Exception.class)
    public void setConfig(List<SiteConfigEntity> updateConfigList){
        siteConfigRepository.saveAll(updateConfigList);
    }
}
