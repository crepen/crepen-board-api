package com.crepen.crepenboard.api.module.user.service;

import ch.qos.logback.core.util.StringUtil;
import com.crepen.crepenboard.api.module.config.model.entity.SiteConfigEntity;
import com.crepen.crepenboard.api.module.config.model.SiteConfigKey;
import com.crepen.crepenboard.api.module.config.service.SiteConfigService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class UserValidateService {

    private final SiteConfigService siteConfigService;


    public boolean isValidId(String id){
        
        // TODO : ID VALIDATION CHECK 구현 필요
        
        return false;
    }

    public boolean isValidEmail (String email) {
        Pattern emailPattern = Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");
        return emailPattern.matcher(email).matches();
    }

    public boolean isValidPassword (String password) {

        List<SiteConfigEntity> passwordConfig = siteConfigService.getPasswordConfig();



        String allowedSymbolStr = passwordConfig.stream()
                .filter(entity -> SiteConfigKey.PASSWORD_ALLOW_SYMBOL.equals(entity.getConfigKey()))
                .toList().stream().findFirst().map(SiteConfigEntity::getConfigValue).orElse("");


        char[] allowedSymbol = StringUtil.isNullOrEmpty(allowedSymbolStr) ? new char[0]  : allowedSymbolStr.toCharArray();




        boolean isRequiredUpperEnglish = passwordConfig.stream()
                .filter(entity -> SiteConfigKey.PASSWORD_REQUIRED_UPPER_ENG.equals(entity.getConfigKey()))
                .toList().stream().findFirst().map(item -> Boolean.parseBoolean(item.getConfigValue())).orElse(false);



        boolean isRequiredNumber = passwordConfig.stream()
                .filter(entity -> SiteConfigKey.PASSWORD_REQUIRED_NUMBER.equals(entity.getConfigKey()))
                .toList().stream().findFirst().map(item -> Boolean.parseBoolean(item.getConfigValue())).orElse(false);

        boolean isRequiredSymbol = passwordConfig.stream()
                .filter(entity -> SiteConfigKey.PASSWORD_REQUIRED_SYMBOL.equals(entity.getConfigKey()))
                .toList().stream().findFirst().map(item -> Boolean.parseBoolean(item.getConfigValue())).orElse(false);

        int minPasswordLength = passwordConfig.stream()
                .filter(entity -> SiteConfigKey.PASSWORD_MINIMUM_LENGTH.equals(entity.getConfigKey()))
                .toList().stream().findFirst().map(item -> Integer.parseInt(item.getConfigValue())).orElse(0);

        int maxPasswordLength = passwordConfig.stream()
                .filter(entity -> SiteConfigKey.PASSWORD_MAXIMUM_LENGTH.equals(entity.getConfigKey()))
                .toList().stream().findFirst().map(item -> Integer.parseInt(item.getConfigValue())).orElse(200);

        StringBuilder passwordRegexStr = new StringBuilder();

        StringBuilder symbolRegexStr = new StringBuilder();

        for(char symbol: allowedSymbol) {
            if(symbol == '-'  || symbol == '[' || symbol == ']' || symbol == '\\') {
                symbolRegexStr.append("\\").append(symbol);
            }
            else{
                symbolRegexStr.append(symbol);
            }
        }

        passwordRegexStr.append("^");

        if(isRequiredUpperEnglish){
            passwordRegexStr.append("(?=.*[A-Z])");
        }

        if(isRequiredNumber){
            passwordRegexStr.append("(?=.*[0-9])");
        }

        if(isRequiredSymbol) {
            passwordRegexStr
                    .append("(?=.*[")
                    .append(symbolRegexStr)
                    .append("])");
        }

        passwordRegexStr
                .append("[")
                .append("A-Za-z0-9")
                .append(symbolRegexStr)
                .append("]");


        passwordRegexStr
                .append("{")
                .append(minPasswordLength > 0 ? minPasswordLength : "")
                .append(",")
                .append(maxPasswordLength)
                .append("}");


        passwordRegexStr.append("$");


        Pattern passwordPattern = Pattern.compile(passwordRegexStr.toString());
        return passwordPattern.matcher(password).matches();
    }

}
