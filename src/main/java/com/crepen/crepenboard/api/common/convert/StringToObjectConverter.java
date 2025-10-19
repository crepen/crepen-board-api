package com.crepen.crepenboard.api.common.convert;

import jakarta.persistence.AttributeConverter;

public class StringToObjectConverter implements AttributeConverter<Object , String> {
    @Override
    public String convertToDatabaseColumn(Object o) {
        return o.toString();
    }

    @Override
    public Object convertToEntityAttribute(String s) {
        return s;
    }
}
