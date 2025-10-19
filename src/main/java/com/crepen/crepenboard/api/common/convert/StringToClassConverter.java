package com.crepen.crepenboard.api.common.convert;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class StringToClassConverter implements AttributeConverter<Class<?> , String> {


    @Override
    public String convertToDatabaseColumn(Class<?> attribute) {
        return attribute == null ? null : attribute.getName();
    }

    @Override
    public Class<?> convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.trim().isEmpty()) {
            return null;
        }

        try {
            return Class.forName(dbData);
        } catch (ClassNotFoundException e) {
            try {
                return Class.forName(String.class.getName());
            } catch (ClassNotFoundException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}
