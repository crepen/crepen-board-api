package com.crepen.crepenboard.api.common.system.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class GlobalConfigVO {
    private String configKey;
    private Object configValue;
    private Class<?> configType;
}
