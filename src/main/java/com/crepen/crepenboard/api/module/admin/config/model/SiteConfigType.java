package com.crepen.crepenboard.api.module.admin.config.model;

public enum SiteConfigType {
    STRING,
    NUMBER,
    BOOLEAN;

    public static boolean contains(String roleString) {
        if (roleString == null) {
            return false;
        }
        try {
            SiteConfigType.valueOf(roleString.toUpperCase());
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
