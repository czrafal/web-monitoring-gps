package com.multisoftware.enums;

public enum TypeFilterEnum {
    ALLROUTE("ALLROUTE"), VEHICLE("VEHICLE"), DRIVER("DRIVER");

    private String value;

    private TypeFilterEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
