package com.zzj.rule.engine.api.script;

/**
 * @author : zhuhongying@bytedance.com
 * @since : 2021/4/6
 */

import com.google.common.collect.Maps;
import com.zzj.rule.engine.api.RuleScript;
import com.zzj.rule.engine.api.enums.Expression;
import com.zzj.rule.engine.api.enums.ExpressionTypeEnum;
import com.zzj.rule.engine.api.enums.KeyTypeEnum;
import com.zzj.rule.engine.api.enums.OperateEnum;
import lombok.SneakyThrows;

import java.util.Map;

/**
 * 表达式节点.
 */
public class ExpressionCodeNode extends BodyCodeNode {

    private final RuleScript rule;

    private static Map<KeyTypeEnum, Map<OperateEnum, String>> typeEnumMapMap = Maps.newHashMap();

    static {
        Map<OperateEnum, String> dateOpMap = Maps.newHashMap();
        dateOpMap.put(OperateEnum.EQ, " RuleUtils.equalsDay(${key}, ${value}) ");
        dateOpMap.put(OperateEnum.NOT_EQ, " !RuleUtils.equalsDay(${key}, ${value}) ");
        dateOpMap.put(OperateEnum.TODAY, " RuleUtils.isToday(${key}) ");
        dateOpMap.put(OperateEnum.CURR_MONTH, " RuleUtils.isCurrentMonth(${key}) ");
        dateOpMap.put(OperateEnum.CURR_YEAR, " RuleUtils.isCurrentYear(${key}) ");
        dateOpMap.put(OperateEnum.LT, "RuleUtils.isBefore(${key}, ${value})");
        dateOpMap.put(OperateEnum.GT, "RuleUtils.isAfter(${key}, ${value})");
        dateOpMap.put(OperateEnum.VOID, " (${key}) != null ");
        typeEnumMapMap.put(KeyTypeEnum.DATE, dateOpMap);

        Map<OperateEnum, String> boolOpMap = Maps.newHashMap();
        boolOpMap.put(OperateEnum.EQ, " ${key} == ${value} ");
        boolOpMap.put(OperateEnum.NOT_EQ, " ${key} != ${value} ");
        boolOpMap.put(OperateEnum.VOID, " (${key}) != null ");
        typeEnumMapMap.put(KeyTypeEnum.BOOLEAN, boolOpMap);

        Map<OperateEnum, String> objectOpMap = Maps.newHashMap();
        objectOpMap.put(OperateEnum.EQ, " ${key} == ${value} ");
        objectOpMap.put(OperateEnum.NOT_EQ, " ${key} != ${value} ");
        objectOpMap.put(OperateEnum.VOID, " (${key}) != null ");
        typeEnumMapMap.put(KeyTypeEnum.OBJECT, objectOpMap);

        Map<OperateEnum, String> textOpMap = Maps.newHashMap();
        textOpMap.put(OperateEnum.EQ, " ${key}.equals(${value}) ");
        textOpMap.put(OperateEnum.NOT_EQ, " !${key}.equals(${value}) ");
        textOpMap.put(OperateEnum.EMPTY, " StringUtils.isBlank(${key}) ");
        textOpMap.put(OperateEnum.NOT_EMPTY, " StringUtils.isNotBlank(${key}) ");
        textOpMap.put(OperateEnum.CONTAIN, " StringUtils.contains(${key}, ${value}) ");
        textOpMap.put(OperateEnum.NOT_CONTAIN, " !StringUtils.contains(${key}, ${value}) ");
        textOpMap.put(OperateEnum.IN, " ${key} in ${value} ");
        textOpMap.put(OperateEnum.NOT_IN, " !(${key} in ${value}) ");
        textOpMap.put(OperateEnum.VOID, " (${key}) != null ");
        typeEnumMapMap.put(KeyTypeEnum.STRING, textOpMap);

        Map<OperateEnum, String> priceOpMap = Maps.newHashMap();
        priceOpMap.put(OperateEnum.EQ, " ${key} == ${value} ");
        priceOpMap.put(OperateEnum.NOT_EQ, " ${key} != ${value} ");
        priceOpMap.put(OperateEnum.GT, " ${key} > ${value} ");
        priceOpMap.put(OperateEnum.GTE, " ${key} >= ${value} ");
        priceOpMap.put(OperateEnum.LT, " ${key} < ${value} ");
        priceOpMap.put(OperateEnum.LTE, " ${key} <= ${value} ");
        priceOpMap.put(OperateEnum.VOID, " (${key}) != null ");
        typeEnumMapMap.put(KeyTypeEnum.DECIMAL, priceOpMap);

        Map<OperateEnum, String> listOpMap = Maps.newHashMap();
        listOpMap.put(OperateEnum.EMPTY, " CollectionUtils.isEmpty(${key}) ");
        listOpMap.put(OperateEnum.NOT_EMPTY, " !CollectionUtils.isEmpty(${key}) ");
        listOpMap.put(OperateEnum.CONTAIN, " ${key}.contains(${value}) ");
        listOpMap.put(OperateEnum.NOT_CONTAIN, " !${key}.contains(${value}) ");
        listOpMap.put(OperateEnum.HAS_INTERSECTION, " RuleUtils.hasIntersection(${key}, ${value}) ");
        listOpMap.put(OperateEnum.VOID, " (${key}) != null ");
        typeEnumMapMap.put(KeyTypeEnum.LIST_STRING, listOpMap);

    }

    public ExpressionCodeNode(RuleScript rule) {
        super(typeEnumMapMap.get(rule.getKeyType()).get(rule.getOp()));
        this.rule = rule;
    }

    @Override
    public String toScript() {
        String script = getName();
        if (script.contains("${key}")) {
            script = script.replace("${key}", rule.getKey().getExpression());
        }
        if (script.contains("${value}")) {
            script = script.replace("${value}", getExpression(rule, rule.getValue()));
        }
        return script;
    }

    @Override
    public String toScriptLog() {
        return toScript();
    }

    @SneakyThrows
    public String getExpression(RuleScript ruleScript, Expression expression) {
        ExpressionTypeEnum expressionType = expression.getExpressionType();
        switch (expressionType) {
            case EXPRESSION:
            case CONST:
            default:
                switch (ruleScript.getKeyType()) {
                    case DATE:
                    case DATETIME:
                    case STRING:
                    case OBJECT:
                    case BOOLEAN:
                    case DECIMAL:
                        if (expression.getExpression() == null) {
                            return "null";
                        }
                        return expression.getExpression();
                }
        }
        return expression.getExpression();
    }

}
