package com.zzj.rule.engine.api;

import com.zzj.rule.engine.api.script.ImportCodeNode;
import com.zzj.rule.engine.api.utils.ArrayUtils;
import com.zzj.rule.engine.api.utils.StringUtils;
import groovy.lang.Binding;
import groovy.lang.Script;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EvalScript {

    private static final String HINT_PREFIX = "/**";

    private static final String HINT_SUFFIX = "*/";

    private static final String SCRIPT_HINT = "script";

    private RuleEngine ruleEngine;

    private List<ImportCodeNode> importNodes;

    private Binding binding;

    private String[] hints;

    private String evalScript;

    private final AtomicBoolean freeze = new AtomicBoolean(false);

    public static EvalScript create(
            RuleEngine ruleEngine,
            List<ImportCodeNode> importNodes,
            Binding binding) {
        return EvalScript.builder()
                .ruleEngine(ruleEngine)
                .importNodes(importNodes)
                .binding(binding)
                .build();
    }

    public final EvalScript setScript(String arg) {
        setHints(StringUtils.substringsBetween(arg, HINT_PREFIX, HINT_SUFFIX));
        if (!ArrayUtils.isEmpty(getHints())) {
            setEvalScript(StringUtils.substringAfterLast(arg, HINT_SUFFIX));
        } else {
            setEvalScript(arg);
        }
        return this;
    }

    /**
     * 执行evalScript脚本，执行完会冻结
     */
    public String eval() {
        if (isFrozen()) {
            return evalScript;
        }
        freeze();
        if (ArrayUtils.contains(getHints(), SCRIPT_HINT)) {
            Script scriptCommand = ruleEngine.getScriptAndCache(
                    getImportNodes().stream().map(ImportCodeNode::toScript).collect(Collectors.joining("\n")) + getEvalScript()
            );
            scriptCommand.setBinding(getBinding());
            return String.valueOf(scriptCommand.run());
        } else {
            return (evalScript);
        }
    }

    private void freeze() {
        freeze.compareAndSet(false, true);
    }

    public boolean isFrozen() {
        return freeze.get();
    }

    public void unfreeze() {
        freeze.set(false);
    }

}
