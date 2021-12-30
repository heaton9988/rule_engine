package com.zzj.rule.engine.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 规则返回指令.
 *
 * @author : zhuhongying@bytedance.com
 * @since : 2020/10/10
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RuleCommand {

    private Boolean active;
    /**
     * 指令有效条件
     */
    private RuleScript activeCondition;

    /**
     * 规则返回指令.
     */
    private String command;

    /**
     * 规则返回指令参数.
     */
    private String[] args;

}
