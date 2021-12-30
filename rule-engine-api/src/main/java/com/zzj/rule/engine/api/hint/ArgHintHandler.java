package com.zzj.rule.engine.api.hint;

import com.zzj.rule.engine.api.EvalScript;

/**
 * 参数Hint处理
 */
public interface ArgHintHandler {
    void handle(EvalScript commandArg);
}