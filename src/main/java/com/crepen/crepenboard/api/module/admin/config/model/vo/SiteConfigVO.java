package com.crepen.crepenboard.api.module.admin.config.model.vo;

import com.crepen.crepenboard.api.module.admin.config.model.SiteConfigType;
import com.crepen.crepenboard.api.module.admin.config.model.entity.SiteConfigEntity;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.persistence.PrePersist;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class SiteConfigVO {
    private String key;
    private Object value;
    private Object defaultValue;
    private String type;
    @Builder.Default
    private boolean isEditable = true;
}
