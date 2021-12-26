package com.zzj.rule.engine.server.entity;

import com.zzj.rule.engine.server.common.framework.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 风险控制规则-头表
 * </p>
 *
 * @author heaton9988
 * @since 2021-12-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class RiskCtrlRuleHeader extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 名称
     */
    private String name;

    /**
     * 描述的多语言码
     */
    private String descLangKey;

    /**
     * 控制等级
     */
    private Integer ctrlLevel;

    /**
     * 消息模板的多语言码
     */
    private String templateLangKey;

    /**
     * 消息展示在哪个字段上
     */
    private String showColumnField;

    /**
     * 表达式的类型 参考RiskCtrlExpressionTypeEnum
     */
    private String expressionType;

    /**
     * aviator表达式
     */
    private String aviatorExpression;

    /**
     * groovy表达式(暂不启用)
     */
    private String groovyExpression;

    /**
     * 状态
     */
    private Integer activeStatus;

    /**
     * 是否已删除
     */
    private Integer isDelete;
}