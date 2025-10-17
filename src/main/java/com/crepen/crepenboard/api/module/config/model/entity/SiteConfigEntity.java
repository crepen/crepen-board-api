package com.crepen.crepenboard.api.module.config.model.entity;

import com.crepen.crepenboard.api.module.config.model.SiteConfigKey;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Enumerated(EnumType.STRING)
    @Column(name = "config_key" , nullable = false , columnDefinition = "VARCHAR(50)")
    private SiteConfigKey configKey;

    @Column(name = "config_value" , nullable = false )
    private String configValue;
}
