package com.zzj.rule.engine.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

/**
 * @author : zhuhongying@bytedance.com
 * @since : 2021/4/6
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Rule {

    /** 规则ID. */
    private String id;

    /** 规则分组. */
    private String triggerEvent;

    /** 规则名称. */
    private String ruleName;

    /** 规则配置信息. */
    private RuleScript ruleScript;

    /** 规则需要引入的依赖类. */
    private Set<String> importClasses;

    /** 规则执行后，如果命中，需执行的指令. */
    private List<RuleCommand> ruleCommands;

    /** 开启调试模式. */
    private Boolean debug;

    /** 是否启用. */
    private Boolean active;

    /** 是否系统级. */
    private Boolean system;

}
