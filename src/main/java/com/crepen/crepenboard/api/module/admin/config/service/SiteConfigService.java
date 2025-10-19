package com.crepen.crepenboard.api.module.admin.config.service;

import ch.qos.logback.core.util.StringUtil;
import com.crepen.crepenboard.api.common.system.model.exception.ResponseException;
import com.crepen.crepenboard.api.module.admin.config.model.SiteConfigType;
import com.crepen.crepenboard.api.module.admin.config.model.entity.SiteConfigEntity;
import com.crepen.crepenboard.api.module.admin.config.model.SiteConfigKey;
import com.crepen.crepenboard.api.module.admin.config.model.exception.SiteConfigException;
import com.crepen.crepenboard.api.module.admin.config.model.vo.SiteConfigVO;
import com.crepen.crepenboard.api.module.admin.config.repository.SiteConfigRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SiteConfigService {

    @Autowired
    private ConversionService conversionService;

    private final SiteConfigRepository siteConfigRepository;


    public boolean isInitialized(){
        Optional<SiteConfigEntity> configInitState = siteConfigRepository.findByConfigKey(SiteConfigKey.SITE_INIT.name());
        try{
            return configInitState.map(
                    siteConfigEntity -> Boolean.TRUE.equals(conversionService.convert(siteConfigEntity.getConfigValue(), Boolean.class))
            ).orElse(Boolean.FALSE);
        }
        catch (Exception e){
            return false;
        }
    }

    public List<SiteConfigEntity> getAllProperties(){
        return siteConfigRepository.findAll();
    }



    @Transactional(rollbackOn = Exception.class)
    public void addConfigObject(List<SiteConfigVO> configList) throws SiteConfigException {
        addConfigObject(configList , false);
    }

    @Transactional(rollbackOn = Exception.class)
    public void addConfigObject(List<SiteConfigVO> configList , boolean forceInstall) throws SiteConfigException {

        if(!configList.stream().filter(item -> StringUtil.isNullOrEmpty(item.getKey())).toList().isEmpty()){
            throw SiteConfigException.ADD_CONFIG_KEY_EMPTY;
        }

        List<SiteConfigEntity> storeConfig = forceInstall ? new ArrayList<>() :
                siteConfigRepository.findAllByConfigKeyIn(configList.stream().map(SiteConfigVO::getKey).toList());

        if(!storeConfig.isEmpty() || configList.stream().map(SiteConfigVO::getKey).toList().stream().distinct().count() != configList.stream().count()){
            throw SiteConfigException.ADD_CONFIG_KEY_DUPLICATED;
        }

        List<SiteConfigEntity> addDataList = new ArrayList<>();

        for (SiteConfigVO configItem : configList){

            // TYPE CHECK
            if(!SiteConfigType.contains(configItem.getType().toUpperCase())){
                throw SiteConfigException.ADD_CONFIG_TYPE_UNDEFINED;
            }

            Class<?> configTypeClass = convertConfigTypeToClass(
                    SiteConfigType.valueOf(configItem.getType().toUpperCase())
            );

            // DEFAULT VALUE TYPE CHECK
            Object defaultValue = null;
            try{
                if(configItem.getDefaultValue() != null){
                    defaultValue = conversionService.convert(configItem.getDefaultValue() , configTypeClass);
                }
                else{
                    throw SiteConfigException.ADD_CONFIG_DEFAULT_VALUE_TYPE_UNMATCHED;
                }
            }
            catch (Exception _){
                throw SiteConfigException.ADD_CONFIG_DEFAULT_VALUE_TYPE_UNMATCHED;
            }


            // VALUE TYPE CHECK
            Object value = null;
            if(!StringUtil.isNullOrEmpty(String.valueOf(configItem.getValue()))){
                try{
                    value= conversionService.convert(configItem.getValue() , configTypeClass);
                }
                catch (Exception _){
                    throw SiteConfigException.ADD_CONFIG_VALUE_TYPE_UNMATCHED;
                }
            }





            addDataList.add(
                    SiteConfigEntity.builder()
                            .configKey(configItem.getKey().toUpperCase())
                            .configType(configTypeClass)
                            .configValue(value == null ? defaultValue : value)
                            .defaultValue(defaultValue)
                            .isEditable(configItem.isEditable())
                            .build()
            );
        }

        siteConfigRepository.saveAll(addDataList);
    }





    private Class<?> convertConfigTypeToClass(SiteConfigType type){
        if(type == SiteConfigType.BOOLEAN){
            return Boolean.class;
        }
        else if(type == SiteConfigType.NUMBER){
            return Integer.class;
        }
        else if(type == SiteConfigType.STRING){
            return String.class;
        }
        else {
            return Object.class;
        }
    }
}
