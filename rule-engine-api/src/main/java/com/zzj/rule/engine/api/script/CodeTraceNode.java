package com.zzj.rule.engine.api.script;

import com.zzj.rule.engine.api.RuleContext;
import com.zzj.rule.engine.api.RuleEngine;
import lombok.SneakyThrows;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * @author : zhuhongying@bytedance.com
 * @since : 2021/4/6
 */
public class CodeTraceNode extends BodyCodeNode {

    private AstNode codeNode;

    private RuleContext ruleContext;

    public CodeTraceNode(String name) {
        super(name);
    }

    public CodeTraceNode(String name, AstNode codeNode, RuleContext ruleContext) {
        super(name);
        this.codeNode = codeNode;
        this.ruleContext = ruleContext;
    }

    @SneakyThrows
    @Override
    public String toScript() {
        String script = codeNode.toScript();
        String scriptContent = encodeToString(script.getBytes("UTF-8"));
        return "new RuleTrace().trace(new Callable() { Boolean call() throws Exception { return " + script + "; }}, " +
                "\"" + ruleContext.getRuleName() + "\"," +
                "\"" + scriptContent + "\", " + ruleContext.getDebug() + ", "
                + RuleEngine.GROOVY_VARIABLES + ", \"" + getName() + "\")";
    }

    @Override
    public String toScriptLog() {
        return codeNode.toScriptLog();
    }


    public static String encodeToString(byte[] src) {
        return src.length == 0 ? "" : new String(encode(src), StandardCharsets.UTF_8);
    }

    public static byte[] encode(byte[] src) {
        return src.length == 0 ? src : Base64.getEncoder().encode(src);
    }
}
