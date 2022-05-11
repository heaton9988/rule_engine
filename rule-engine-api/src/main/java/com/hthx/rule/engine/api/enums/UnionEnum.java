package com.hthx.rule.engine.api.enums;

import lombok.Data;

/**
 * 聚合类型.
 */
public enum UnionEnum {
    AND("AND", "&&", "&&", "并且"),
    OR("OR", "||", "\\|\\|", "或者");

    private String code;
    private String expression;
    private String regex;
    private String desc;

    UnionEnum(String code, String expression, String regex, String desc) {
        this.code = code;
        this.expression = expression;
        this.regex = regex;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public String getExpression() {
        return expression;
    }

    public String getRegex() {
        return regex;
    }

    public String getDesc() {
        return desc;
    }
}