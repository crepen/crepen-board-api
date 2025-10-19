package com.crepen.crepenboard.api.common.system.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

@Component

public class GlobalConfigureService {

    @Autowired
    private ConversionService conversionService;
    private final Map<String, String> globalConfig = new ConcurrentHashMap<>();

    public String get(String key){
        return globalConfig.get(key);
    }

    public String get(String key, String defaultValue){
        return Objects.toString(globalConfig.get(key) , defaultValue);
    }

    public <T> T get(String key , Class<T> type){
        return get(key , null , type);
    }

    public <T> T get(String key , T defaultValue , Class<T> type){
        try{
            String value = globalConfig.get(key);

            if (value == null || value.trim().isEmpty()) {
                return defaultValue;
            }

            return conversionService.convert(value, type);
        }
        catch (Exception ex){
            return defaultValue;
        }
    }

    public void set(String key, String value){
        globalConfig.put(key, value);
    }

    public void setAll(Map<String , String> list){
        globalConfig.putAll(list);
    }

    public void clear() {
        globalConfig.clear();
    }
}
