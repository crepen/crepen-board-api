package com.crepen.crepenboard.api.module.config.repository;

import com.crepen.crepenboard.api.module.config.model.entity.SiteConfigEntity;
import com.crepen.crepenboard.api.module.config.model.SiteConfigKey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SiteConfigRepository extends JpaRepository<SiteConfigEntity, Long> {
    Optional<SiteConfigEntity> findByConfigKey(SiteConfigKey configKey);
    List<SiteConfigEntity> findAllByConfigKeyIn(List<SiteConfigKey> configKey);
}
