package com.zzj.rule.server.entity;

import java.time.LocalDateTime;
import java.io.Serializable;

import com.zzj.rule.server.common.framework.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 系统-风险控制规则-触发器的一系列规则
 * </p>
 *
 * @author heaton9988
 * @since 2021-12-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class RiskCtrlTriggerAssignRule extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * risk_ctrl_rule_header表的id
     */
    private Long riskCtrlRuleHeaderId;

    /**
     * 触发器code
     */
    private String triggerCode;
}