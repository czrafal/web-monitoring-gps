package com.multisoftware.enums;

public enum DateFilterEnum {

    DATE("DATE"), DATERANGE("DATERANGE"), MONTH("MONTH");

    private String value;

    private DateFilterEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
