package com.hthx.rule.engine.api;

import com.hthx.rule.engine.api.enums.OperatorEnum;
import com.hthx.rule.engine.api.enums.ValueTypeEnum;
import lombok.Builder;
import lombok.ToString;

import java.util.Objects;

@ToString
@Builder
public class Condition implements Expression {
    private Key key1;
    private Key key2;
    private Key key3;
    private OperatorEnum operatorEnum;

    public Condition(Key key1, Key key2, Key key3, OperatorEnum operatorEnum) {
        this.key1 = key1;
        this.key2 = key2;
        this.key3 = key3;
        this.operatorEnum = operatorEnum;
    }

    @Override
    public String getAviatorExpression() throws Exception {
        this.check();

        if (OperatorEnum.RANGE.equals(operatorEnum)) {
            return key1.getAviatorExpression() + " >= " + key2.getAviatorExpression() + " && " + key1.getAviatorExpression() + " <= " + key3.getAviatorExpression();
        } else if (OperatorEnum.EQ.equals(operatorEnum)) {
            return key1.getAviatorExpression() + " == " + key2.getAviatorExpression();
        } else if (OperatorEnum.NOT_EQ.equals(operatorEnum)) {
            return key1.getAviatorExpression() + " != " + key2.getAviatorExpression();
        } else if (OperatorEnum.GT.equals(operatorEnum)) {
            return key1.getAviatorExpression() + " > " + key2.getAviatorExpression();
        } else if (OperatorEnum.GTE.equals(operatorEnum)) {
            return key1.getAviatorExpression() + " >= " + key2.getAviatorExpression();
        } else if (OperatorEnum.LT.equals(operatorEnum)) {
            return key1.getAviatorExpression() + " < " + key2.getAviatorExpression();
        } else if (OperatorEnum.LTE.equals(operatorEnum)) {
            return key1.getAviatorExpression() + " <= " + key2.getAviatorExpression();
        }
        return null;
    }

    @Override
    public ValueTypeEnum getValueType() {
        return ValueTypeEnum.BOOLEAN;
    }

    @Override
    public void check() throws Exception {
        ValueTypeEnum valueType1 = key1 == null ? null : key1.getValueType();
        ValueTypeEnum valueType2 = key2 == null ? null : key2.getValueType();
        ValueTypeEnum valueType3 = key3 == null ? null : key3.getValueType();

        if (!Objects.equals(valueType1, valueType2)) throw new Exception("valueType1 != valueType2: " + this);

        if (OperatorEnum.RANGE.equals(operatorEnum)) {
            if (!Objects.equals(valueType2, valueType3)) throw new Exception("valueType2 != valueType3: " + this);
        } else if (OperatorEnum.EQ.equals(operatorEnum)) {
            if (key3 != null) throw new Exception("key3 should be null: " + this);
        } else if (OperatorEnum.NOT_EQ.equals(operatorEnum)) {
            if (key3 != null) throw new Exception("key3 should be null: " + this);
        } else if (OperatorEnum.GT.equals(operatorEnum)) {
            if (key3 != null) throw new Exception("key3 should be null: " + this);
        } else if (OperatorEnum.GTE.equals(operatorEnum)) {
            if (key3 != null) throw new Exception("key3 should be null: " + this);
        } else if (OperatorEnum.LT.equals(operatorEnum)) {
            if (key3 != null) throw new Exception("key3 should be null: " + this);
        } else if (OperatorEnum.LTE.equals(operatorEnum)) {
            if (key3 != null) throw new Exception("key3 should be null: " + this);
        }
    }
}