package com.hthx.rule.engine.api;

import com.hthx.rule.engine.api.enums.ValueTypeEnum;

public interface Expression {
    String getAviatorExpression() throws Exception;

    ValueTypeEnum getValueType();

    void check() throws Exception;
}