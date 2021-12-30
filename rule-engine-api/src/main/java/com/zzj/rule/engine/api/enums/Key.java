package com.zzj.rule.engine.api.enums;

import lombok.Data;

/**
 * @author : zhuhongying@bytedance.com
 * @since : 2020/10/16
 */
@Data
public class Key implements Expression {

    private ExpressionTypeEnum expressionType;

    private String expression;

}
