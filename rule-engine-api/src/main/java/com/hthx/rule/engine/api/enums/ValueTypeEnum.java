package com.hthx.rule.engine.api.enums;

public enum ValueTypeEnum {
    STRING, BOOLEAN, LONG, DOUBLE;

    public boolean blankStringTypes(ValueTypeEnum e) {
        if (STRING.equals(e)) {
            return true;
        } else {
            return false;
        }
    }
}