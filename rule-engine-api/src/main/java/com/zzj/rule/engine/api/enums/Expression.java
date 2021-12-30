package com.zzj.rule.engine.api.enums;

/**
 * @author : zhuhongying@bytedance.com
 * @since : 2020/10/16
 */
public interface Expression {

    ExpressionTypeEnum getExpressionType();

    String getExpression();

}
