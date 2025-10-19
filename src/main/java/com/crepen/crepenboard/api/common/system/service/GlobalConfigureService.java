package com.crepen.crepenboard.api.common.system.service;

import com.crepen.crepenboard.api.common.system.model.GlobalConfigVO;
import com.crepen.crepenboard.api.module.admin.config.model.SiteConfigKey;
import com.crepen.crepenboard.api.module.admin.config.model.exception.SiteConfigException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class GlobalConfigureService {

    @Autowired
    private ConversionService conversionService;
    private final List<GlobalConfigVO> globalConfig = new ArrayList<>();

    public Optional<GlobalConfigVO> get(String key){
        List<GlobalConfigVO> matchList = globalConfig.stream().filter(conf -> conf.getConfigKey().equals(key.toUpperCase())).toList();
        return matchList.isEmpty() ? Optional.empty() : Optional.ofNullable(matchList.getFirst());
    }

    public <T> T get(String key , T defaultValue , Class<T> type){
        Optional<GlobalConfigVO> conf = get(key);

        if(conf.isEmpty()){
            return defaultValue;
        }
        else {
            try{
                Object value = conf.get().getConfigValue();
                return conversionService.convert(value, type);
            }
            catch (Exception _){
                return defaultValue;
            }

        }
    }

    public String get(String key , String defaultValue){
        return get(key , defaultValue , String.class);
    }

    public void set(String key , Object value , Class<?> classType) throws SiteConfigException {
        try{
            SiteConfigKey keyType = SiteConfigKey.valueOf(key.toUpperCase());

        }
        catch (Exception _){
            throw SiteConfigException.getConfigNotMatchException(key);
        }

//        if(!classType.isInstance(value)){
//            throw SiteConfigException.CONFIG_VALUE_TYPE_UNMATCHED;
//        }

        globalConfig.add(
                GlobalConfigVO.builder()
                        .configKey(key.toUpperCase())
                        .configValue(conversionService.convert(value , classType))
                        .configType(classType)
                        .build()
        );

    }

    public void setAll(List<GlobalConfigVO> globalConfigList) throws SiteConfigException {
        for(GlobalConfigVO globalConfigVO : globalConfigList){
            set(globalConfigVO.getConfigKey() , globalConfigVO.getConfigValue() , globalConfigVO.getConfigType());
        }
    }

    public void clear(){
        globalConfig.clear();
    }


//    public String get(String key){
//
//
//        return globalConfig.get(key);
//    }
//
//    public String get(String key, String defaultValue){
//        return Objects.toString(globalConfig.get(key) , defaultValue);
//    }
//
//    public <T> T get(String key , Class<T> type){
//        return get(key , null , type);
//    }
//
//    public <T> T get(String key , T defaultValue , Class<T> type){
//        try{
//            String value = globalConfig.get(key);
//
//            if (value == null || value.trim().isEmpty()) {
//                return defaultValue;
//            }
//
//            return conversionService.convert(value, type);
//        }
//        catch (Exception ex){
//            return defaultValue;
//        }
//    }
//
//    public void set(String key, String value){
//        globalConfig.put(key, value);
//    }
//
//    public void setAll(Map<String , String> list){
//        globalConfig.putAll(list);
//    }
//
//    public void clear() {
//        globalConfig.clear();
//    }
}
