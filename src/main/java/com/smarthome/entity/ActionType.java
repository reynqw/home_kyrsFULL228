package com.smarthome.entity;

public enum ActionType {
    TURN_ON("ВКЛ"),
    TURN_OFF("ВЫКЛ"),
    SET("УСТАНОВИТЬ");

    private final String value;

    ActionType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
} 