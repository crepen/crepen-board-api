package com.crepen.crepenboard.api.module.admin.config.controller;

import com.crepen.crepenboard.api.common.system.model.BaseResponse;
import com.crepen.crepenboard.api.common.system.model.exception.ResponseException;
import com.crepen.crepenboard.api.module.admin.config.model.dto.EditConfigDTO;
import com.crepen.crepenboard.api.module.admin.config.model.exception.SiteConfigException;
import com.crepen.crepenboard.api.module.admin.config.service.SiteConfigService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/admin/config")
@RequiredArgsConstructor
public class AdminSiteConfigController {

    private final SiteConfigService siteConfigService;

    @PutMapping
    public ResponseEntity<BaseResponse> editConfig(
            @RequestBody EditConfigDTO reqBody
    ) throws ResponseException {


        return BaseResponse.success().toResponseEntity();
    }

    @PostMapping
    public ResponseEntity<BaseResponse> addConfig(
            @RequestBody EditConfigDTO reqBody
    ) throws SiteConfigException {
        siteConfigService.addConfigObject(reqBody.getConfig().stream().peek(item -> item.setEditable(true)).toList());
        return BaseResponse.success().toResponseEntity();
    }
}
