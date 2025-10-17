package com.crepen.crepenboard.api.module.config.model;

import lombok.Builder;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Builder
public class UpdateSiteConfigVO {
    private Map<SiteConfigKey , String> updateList;

    public void append(String key , String value) {
        try{

            SiteConfigKey convKey = SiteConfigKey.valueOf(key.toUpperCase());
            this.append(convKey , value);
        }
        catch (Exception ex){

        }

    }

    public void append(SiteConfigKey key , String value) {
        if(updateList == null) updateList = new HashMap<>();
        updateList.put(key,value);
    }
}
