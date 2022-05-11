package com.hthx.rule.engine.api;


import com.hthx.rule.engine.api.enums.ValueTypeEnum;
import com.hthx.rule.engine.api.enums.KeyTypeEnum;
import lombok.Builder;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

@ToString
@Builder
public class Key implements Expression {
    private KeyTypeEnum keyTypeEnum;
    private String expression;
    private ValueTypeEnum valueTypeEnum;

    public Key(KeyTypeEnum keyTypeEnum, String expression, ValueTypeEnum valueTypeEnum) {
        this.keyTypeEnum = keyTypeEnum;
        this.expression = expression;
        this.valueTypeEnum = valueTypeEnum;
    }

    @Override
    public String getAviatorExpression() {
        if (KeyTypeEnum.CONSTANT.equals(keyTypeEnum) && ValueTypeEnum.STRING.equals(valueTypeEnum)) {
            return "'" + expression + "'";
        }
        return expression;
    }

    @Override
    public ValueTypeEnum getValueType() {
        return valueTypeEnum;
    }

    @Override
    public void check() throws Exception {
        if (expression == null) throw new Exception("expression cannot be null: " + this);

        if (StringUtils.isBlank(expression) && !valueTypeEnum.blankStringTypes(valueTypeEnum)) {
            throw new Exception("expression is blank: " + this);
        }

        if (KeyTypeEnum.CONSTANT.equals(keyTypeEnum)) {
            try {
                if (ValueTypeEnum.STRING.equals(valueTypeEnum)) {
                    // do nothing
                } else if (ValueTypeEnum.BOOLEAN.equals(valueTypeEnum)) {
                    if (!expression.equals("true") && !expression.equals("false"))
                        throw new Exception("");
                } else if (ValueTypeEnum.LONG.equals(valueTypeEnum)) {
                    Long.parseLong(expression);
                } else if (ValueTypeEnum.DOUBLE.equals(valueTypeEnum)) {
                    Double.parseDouble(expression);
                }
            } catch (Exception e) {
                throw new Exception("value type of constant is error: " + this);
            }
        }
    }
}