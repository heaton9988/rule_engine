package com.zzj.rule.engine.server.entity;

import com.zzj.rule.engine.server.common.framework.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 风险控制规则-明细表
 * </p>
 *
 * @author heaton9988
 * @since 2021-12-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class RiskCtrlRuleDetail extends BaseEntity<RiskCtrlRuleDetail> {

    private static final long serialVersionUID = 1L;

    /**
     * risk_ctrl_rule_header表的id
     */
    private Long riskCtrlRuleHeaderId;

    /**
     * 组件code
     */
    private String componentCode;

    /**
     * 组件字段code
     */
    private String componentFieldCode;

    /**
     * 操作符
     */
    private String operatorType;

    /**
     * 校验模式
     */
    private String checkMode;

    /**
     * 内置函数名code
     */
    private String functionCode;
}