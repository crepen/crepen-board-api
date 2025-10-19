package com.crepen.crepenboard.api.module.admin.config.model.exception;

import com.crepen.crepenboard.api.common.system.model.exception.ResponseException;

public class SiteConfigException extends ResponseException {
    SiteConfigException(int statusCode , String errorCode , String message , String... args){
        super(statusCode ,errorCode , message , args);
    }


    public static SiteConfigException getConfigNotMatchException(String notMatchKey){
        return new SiteConfigException(403 , "CONFIG_KEY_NOT_FOUND" , "error.config.key_not_found_args" , notMatchKey);
    }

    public static SiteConfigException ADD_CONFIG_VALUE_TYPE_UNMATCHED = new SiteConfigException(403 , "CONFIG_VALUE_TYPE_UNMATCHED" , "error.config.value_type_unmatched");
    public static SiteConfigException ADD_CONFIG_KEY_EMPTY = new SiteConfigException(403 , "ADD_CONFIG_KEY_EMPTY" , "error.config.key_empty");
    public static SiteConfigException ADD_CONFIG_KEY_DUPLICATED = new SiteConfigException(403,  "ADD_CONFIG_KEY_DUPLICATED" , "error.config.key_duplicated");
    public static SiteConfigException ADD_CONFIG_DEFAULT_VALUE_TYPE_UNMATCHED = new SiteConfigException(403 , "ADD_CONFIG_DEFAULT_VALUE_TYPE_UNMATCHED" , "error.config.default_value_type_unmatched");
    public static SiteConfigException ADD_CONFIG_TYPE_UNDEFINED = new SiteConfigException(403 , "ADD_CONFIG_TYPE_UNDEFINED" , "error.config.type_undefined");
}
