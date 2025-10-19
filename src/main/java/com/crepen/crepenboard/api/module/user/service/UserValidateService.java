package com.crepen.crepenboard.api.module.user.service;

import ch.qos.logback.core.util.StringUtil;
import com.crepen.crepenboard.api.common.system.service.GlobalConfigureService;
import com.crepen.crepenboard.api.module.config.model.SiteConfigKey;
import com.crepen.crepenboard.api.module.config.service.SiteConfigService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class UserValidateService {

    private final GlobalConfigureService globalConfigureService;


    public boolean isValidId(String id){

        int minLength = Integer.parseInt(
                globalConfigureService.get(SiteConfigKey.ACCOUNT_ID_MINIMUM_LENGTH.name())
        );

        int maxLength = Integer.parseInt(
                globalConfigureService.get(SiteConfigKey.ACCOUNT_ID_MAXIMUM_LENGTH.name())
        );

        String allowIdSymbol = globalConfigureService.get(SiteConfigKey.ACCOUNT_ID_ALLOW_SYMBOL.name());

        System.out.println();

        Pattern idPattern = Pattern.compile(String.format("^[\\w%s]{%d,%d}$" , allowIdSymbol, minLength, maxLength));
        return idPattern.matcher(id).matches();
    }

    public boolean isValidEmail (String email) {
        Pattern emailPattern = Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");
        return emailPattern.matcher(email).matches();
    }

    public boolean isValidPassword (String password) {

        String allowedSymbolStr = globalConfigureService.get(
                SiteConfigKey.ACCOUNT_PASSWORD_ALLOW_SYMBOL.name(),
                ""
        );

        char[] allowedSymbol = StringUtil.isNullOrEmpty(allowedSymbolStr) ? new char[0]  : allowedSymbolStr.toCharArray();

        boolean isRequiredUpperEnglish = globalConfigureService.get(
                SiteConfigKey.ACCOUNT_PASSWORD_REQUIRED_UPPER_ENG.name(),
                true,
                Boolean.class
        );

        boolean isRequiredNumber = globalConfigureService.get(
                SiteConfigKey.ACCOUNT_PASSWORD_REQUIRED_NUMBER.name(),
                true,
                Boolean.class
        );

        boolean isRequiredSymbol =  globalConfigureService.get(
                SiteConfigKey.ACCOUNT_PASSWORD_REQUIRED_SYMBOL.name(),
                true,
                Boolean.class
        );

        int minPasswordLength = globalConfigureService.get(
                SiteConfigKey.ACCOUNT_PASSWORD_MINIMUM_LENGTH.name(),
                8,
                Integer.class
        );

        int maxPasswordLength = globalConfigureService.get(
                SiteConfigKey.ACCOUNT_PASSWORD_MAXIMUM_LENGTH.name(),
                12,
                Integer.class
        );


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
