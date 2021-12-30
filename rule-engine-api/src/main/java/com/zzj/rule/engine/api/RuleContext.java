package com.zzj.rule.engine.api;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Map;

/**
 * 规则上下文.
 * @author : zhuhongying@bytedance.com
 * @since : 2020/10/10
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class RuleContext extends Rule {

    /** 规则执行上下文变量. */
    private Map<String, Object> variables;

    public RuleContext(Rule rule) {
        setId(rule.getId());
        setTriggerEvent(rule.getTriggerEvent());
        setRuleName(rule.getRuleName());
        setImportClasses(rule.getImportClasses());
        setRuleScript(rule.getRuleScript());
        setRuleCommands(rule.getRuleCommands());
        setDebug(rule.getDebug());
        setActive(rule.getActive());
        setSystem(rule.getSystem());
    }

}
