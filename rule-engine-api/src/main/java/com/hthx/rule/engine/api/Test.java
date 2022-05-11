package com.hthx.rule.engine.api;

import com.google.common.collect.Lists;
import com.hthx.rule.engine.api.enums.KeyTypeEnum;
import com.hthx.rule.engine.api.enums.OperatorEnum;
import com.hthx.rule.engine.api.enums.UnionEnum;
import com.hthx.rule.engine.api.enums.ValueTypeEnum;

public class Test {
    public static void main(String[] args) throws Exception {
        {
            Condition condition = Condition.builder()
                    .operatorEnum(OperatorEnum.EQ)
                    .key1(new Key(KeyTypeEnum.VARIABLE, "ziBuDiZhai", ValueTypeEnum.STRING))
                    .key2(new Key(KeyTypeEnum.CONSTANT, "是", ValueTypeEnum.STRING))
                    .build();
            System.out.println("Condition EQ:\t" + condition.getAviatorExpression());
        }
        {
            Condition condition = Condition.builder()
                    .operatorEnum(OperatorEnum.RANGE)
                    .key1(new Key(KeyTypeEnum.VARIABLE, "ziBuDiZhai", ValueTypeEnum.STRING))
                    .key2(new Key(KeyTypeEnum.CONSTANT, "1", ValueTypeEnum.STRING))
                    .key3(new Key(KeyTypeEnum.CONSTANT, "10", ValueTypeEnum.STRING))
                    .build();
            System.out.println("Condition RANGE:\t" + condition.getAviatorExpression());
        }
        {
            Condition condition = Condition.builder()
                    .operatorEnum(OperatorEnum.GT)
                    .key1(new Key(KeyTypeEnum.VARIABLE, "ziBuDiZhai", ValueTypeEnum.STRING))
                    .key2(new Key(KeyTypeEnum.CONSTANT, "1", ValueTypeEnum.STRING))
                    .build();
            System.out.println("Condition GT:\t" + condition.getAviatorExpression());
        }
        {
            Condition condition1 = Condition.builder()
                    .operatorEnum(OperatorEnum.RANGE)
                    .key1(new Key(KeyTypeEnum.VARIABLE, "ziBuDiZhai", ValueTypeEnum.LONG))
                    .key2(new Key(KeyTypeEnum.CONSTANT, "1", ValueTypeEnum.LONG))
                    .key3(new Key(KeyTypeEnum.CONSTANT, "10", ValueTypeEnum.LONG))
                    .build();
            Condition condition2 = Condition.builder()
                    .operatorEnum(OperatorEnum.GT)
                    .key1(new Key(KeyTypeEnum.VARIABLE, "XiaTiao", ValueTypeEnum.LONG))
                    .key2(new Key(KeyTypeEnum.CONSTANT, "1", ValueTypeEnum.LONG))
                    .build();
            ConditionGroup conditionGroup = ConditionGroup.builder()
                    .conditionList(Lists.newArrayList(condition1, condition2, condition2, condition1))
                    .unionEnum(UnionEnum.OR)
                    .build();
            System.out.println("ConditionGroup:\t" + conditionGroup.getAviatorExpression());
        }
    }
}