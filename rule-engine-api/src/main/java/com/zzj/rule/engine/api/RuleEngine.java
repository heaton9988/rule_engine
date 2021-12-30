package com.zzj.rule.engine.api;

import com.google.common.collect.Lists;
import com.zzj.rule.engine.api.hint.ArgHint;
import com.zzj.rule.engine.api.script.BodyCodeNode;
import com.zzj.rule.engine.api.script.MainCodeNode;
import com.zzj.rule.engine.api.transformer.ScriptTransformer;
import com.zzj.rule.engine.api.utils.BooleanUtils;
import com.zzj.rule.engine.api.utils.JSONUtils;
import com.zzj.rule.engine.api.utils.MD5Utils;
import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import groovy.lang.Script;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * 规则执行引擎.
 *
 * @author : zhuhongying@bytedance.com
 * @since : 2020/10/9
 */
@Slf4j
public class RuleEngine {

    public static final String GROOVY_VARIABLES = "GROOVY_VARIABLES";

    private final ScriptTransformer ruleTransformer = new ScriptTransformer();

    private final List<ArgHint> hints = Lists.newArrayList();

    private static final GroovyShell groovyShell;

    static {
        groovyShell = new GroovyShell();
    }

    /**
     * 解析并执行规则模型.
     */
    public final void compile(Rule rule) {
        compile(new RuleContext(rule));
    }

    /**
     * 解析并执行规则模型.
     */
    public final void compile(RuleContext ruleContext) {
        if (BooleanUtils.isFalse(ruleContext.getActive())) {
            return;
        }
        getScriptAndCache(ruleTransformer.transform(ruleContext).toScript());
    }

    /**
     * 解析并执行规则模型.
     */
    public final List<RuleCommand> execute(RuleContext ruleContext) {
        if (BooleanUtils.isFalse(ruleContext.getActive())) {
            return Lists.newArrayList();
        }
        return executeScript(ruleTransformer.transform(ruleContext), ruleContext);
    }

    /**
     * 执行规则脚本.
     *
     * @param groovyNode  可执行规则脚本
     * @param ruleContext 规则上下文
     * @return 规则执行结果
     */
    private List<RuleCommand> executeScript(MainCodeNode groovyNode, RuleContext ruleContext) {
        try {
            Script script = getScriptAndCache(groovyNode.toScript());
            synchronized (script) {
                Binding binding = new Binding();
                ruleContext.getVariables().forEach((binding::setVariable));
                binding.setVariable(GROOVY_VARIABLES, binding.getVariables());
                script.setBinding(binding);
                if ((boolean) script.run()) {
                    List<RuleCommand> ruleCommands = JSONUtils.parseList(JSONUtils.toJSONString(ruleContext.getRuleCommands()),
                        RuleCommand.class);
                    List<RuleCommand> outputCommands = Lists.newArrayList();
                    for (RuleCommand ruleCommand : ruleCommands) {
                        ruleCommand.setActive(true);
                        // 如果有指令生效条件，判断当前指令是否生效
                        if (ruleCommand.getActiveCondition() != null) {
                            BodyCodeNode activeConditionCode = BodyCodeNode.generateCodeByRule(ruleContext, null, ruleCommand.getActiveCondition(), 1);
                            ruleCommand.setActive(Boolean.valueOf(evalScript(groovyNode, binding, "/** script */return " + activeConditionCode.toScript())));
                        }
                        if (BooleanUtils.isFalse(ruleCommand.getActive())) {
                            continue;
                        }
                        // 动态Hint处理指令参数
                        Object[] args = ruleCommand.getArgs();
                        for (int i = 0; i < args.length; i++) {
                            args[i] = evalScript(groovyNode, binding, (String) args[i]);
                        }
                        outputCommands.add(ruleCommand);
                    }
                    return outputCommands;
                }
            }
        } catch (Exception e) {
//            log.error("failed execute rule [{}]: {} ; script: {}", ruleContext.getRuleName(), e.getMessage(), groovyNode.toScriptLog(), e);
        }
        return Lists.newArrayList();
    }

    private String evalScript(
        MainCodeNode groovyNode,
        Binding binding,
        String commandScript) {
        EvalScript evalScript = EvalScript.create(this, groovyNode.getImportNodes(), binding).setScript(commandScript);
        hints.stream()
            .filter(hint -> hint.matchHint(evalScript.getHints()))
            .forEach(hint -> hint.getHandler().handle(evalScript));
        return evalScript.eval();
    }

    public Script getScriptAndCache(String scriptExpression) {
        String scriptIdentity = MD5Utils.getMD5Str(scriptExpression);
        Script script = RuleCache.getValue(RuleCache.GROOVY_SHELL_KEY_PREFIX + scriptIdentity,
            () -> Optional.ofNullable(groovyShell.parse(scriptExpression)));
        if (script == null) {
            script = groovyShell.parse(scriptExpression);
        }
        return script;
    }

    public final void addHint(ArgHint hint) {
        if (hints.stream().anyMatch(argHint ->
            argHint.getClass().getName().equals(hint.getClass().getName()))) {
            return;
        }
        hints.add(hint);
        Collections.sort(hints);
    }
}