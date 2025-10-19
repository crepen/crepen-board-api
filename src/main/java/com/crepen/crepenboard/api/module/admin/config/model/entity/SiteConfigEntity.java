package com.crepen.crepenboard.api.module.admin.config.model.entity;

import com.crepen.crepenboard.api.common.convert.StringToClassConverter;
import com.crepen.crepenboard.api.common.convert.StringToObjectConverter;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@Table(name="config")
@NoArgsConstructor
@AllArgsConstructor
public class SiteConfigEntity {
    @Id
    @Column(name = "config_key" , nullable = false , length = 50)
    private String configKey;

    @Convert(converter = StringToObjectConverter.class)
    @Column(name = "config_value" ,  columnDefinition = "VARCHAR(200)")
    private Object configValue;

    @Convert(converter = StringToClassConverter.class)
    @Column(name = "config_type" , nullable = false , columnDefinition = "VARCHAR(100)")
    private Class<?> configType;

    @Convert(converter = StringToObjectConverter.class)
    @Column(name = "default_value" , columnDefinition = "VARCHAR(200)")
    private Object defaultValue;

    @Column(name = "is_editable" , nullable = false , columnDefinition = "BOOLEAN default TRUE")
    private boolean isEditable;





}
