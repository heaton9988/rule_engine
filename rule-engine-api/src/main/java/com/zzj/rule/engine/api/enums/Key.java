package com.zzj.rule.engine.api.enums;

import lombok.Data;

/**
 */
@Data
public class Key implements Expression {

    private ExpressionTypeEnum expressionType;

    private String expression;

}
