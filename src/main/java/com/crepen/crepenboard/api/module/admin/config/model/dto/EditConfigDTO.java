package com.crepen.crepenboard.api.module.admin.config.model.dto;

import com.crepen.crepenboard.api.module.admin.config.model.vo.SiteConfigVO;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class EditConfigDTO {
    private List<SiteConfigVO> config;
}
