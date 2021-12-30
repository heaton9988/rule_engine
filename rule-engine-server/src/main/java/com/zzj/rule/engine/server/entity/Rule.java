package com.zzj.rule.engine.server.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;

import com.zzj.rule.engine.server.common.framework.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 规则配置表
 * </p>
 *
 * @author heaton9988
 * @since 2021-12-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_rule")
public class Rule extends BaseEntity<Rule> {

    private static final long serialVersionUID = 1L;

    /**
     * 规则类型(查询方案/合规校验)
     */
    private String ruleType;

    /**
     * 规则名称/查询方案名称
     */
    private String ruleName;

    /**
     * 规则脚本
     */
    private String ruleScript;

    /**
     * 输出指令
     */
    private String ruleCommand;

    /**
     * 执行阶段
     */
    private String executionPhase;

    /**
     * 租户编码
     */
    private String tenantUnionId;

    /**
     * 业务域编码
     */
    private String groupUnionId;

    /**
     * 是否删除
     */
    private Integer isDeleted;
}