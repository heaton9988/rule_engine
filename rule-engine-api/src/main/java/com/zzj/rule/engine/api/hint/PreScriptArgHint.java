package com.zzj.rule.engine.api.hint;

import com.zzj.rule.engine.api.utils.ArrayUtils;
import lombok.extern.slf4j.Slf4j;

/**
 * 预先的脚本处理, 优先把后面字符串当作代码执行，再执行其他hint
 * 比如后面字符串是个变量，他的值 需要从上下文先拿，拿到以后再当作普通的I18n处理
 */
@Slf4j
public class PreScriptArgHint implements ArgHint {
    private static final String PRE_SCRIPT_HINT = "pre-script";

    @Override
    public int order() {
        return 10;
    }

    @Override
    public boolean matchHint(String[] hints) {
        return ArrayUtils.contains(hints, PRE_SCRIPT_HINT);
    }

    @Override
    public ArgHintHandler getHandler() {
        return commandArg -> {
            commandArg.unfreeze();
            commandArg.setEvalScript(commandArg.eval());
            commandArg.unfreeze();
        };
    }
}