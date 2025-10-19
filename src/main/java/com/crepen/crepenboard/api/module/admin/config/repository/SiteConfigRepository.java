package com.crepen.crepenboard.api.module.admin.config.repository;

import com.crepen.crepenboard.api.module.admin.config.model.entity.SiteConfigEntity;
import com.crepen.crepenboard.api.module.admin.config.model.SiteConfigKey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SiteConfigRepository extends JpaRepository<SiteConfigEntity, Long> {
    List<SiteConfigEntity> findAllByConfigKeyIn(List<String> configKey);

    Optional<SiteConfigEntity> findByConfigKey(String configKey);
}
