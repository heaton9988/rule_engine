package com.hthx.rule.engine.api;

import com.hthx.rule.engine.api.enums.ValueTypeEnum;
import com.hthx.rule.engine.api.enums.UnionEnum;
import lombok.Builder;
import lombok.ToString;
import org.apache.commons.collections.CollectionUtils;

import java.util.List;

@ToString
@Builder
public class ConditionGroup implements Expression {
    private UnionEnum unionEnum;
    private List<Condition> conditionList;

    @Override
    public String getAviatorExpression() throws Exception {
        this.check();

        StringBuilder sb = new StringBuilder();
        for (Condition condition : conditionList) {
            sb.append(" ").append(unionEnum.getExpression()).append(" ( ").append(condition.getAviatorExpression()).append(" )");
        }
        return sb.toString().replaceFirst(" " + unionEnum.getRegex() + " ", "");
    }

    @Override
    public ValueTypeEnum getValueType() {
        return ValueTypeEnum.BOOLEAN;
    }

    @Override
    public void check() throws Exception {
        if (CollectionUtils.isEmpty(conditionList)) throw new Exception("conditionList is empty");
    }
}