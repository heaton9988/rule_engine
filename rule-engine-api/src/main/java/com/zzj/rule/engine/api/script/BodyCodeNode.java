package com.zzj.rule.engine.api.script;


import com.zzj.rule.engine.api.RuleContext;
import com.zzj.rule.engine.api.RuleScript;

/**
 * @author : zhuhongying@bytedance.com
 * @since : 2021/4/6
 */
public class BodyCodeNode extends AstNode {

    public BodyCodeNode(String code) {
        super(code);
    }

    @Override
    public String toScript() {
        return getName();
    }

    public static BodyCodeNode generateCodeByRule(RuleContext ruleContext,
                                                  RuleScript parentScript,
                                                  RuleScript script,
                                                  int index) {
        if (parentScript == null) {
            script.setScriptIndex(String.valueOf(index));
        } else {
            script.setScriptIndex(parentScript.getScriptIndex() + "." + index);
        }
        return script.isUnion() ? new UnionCodeNode(ruleContext, script) :
            new CodeTraceNode(script.getScriptIndex(), new ExpressionCodeNode(script), ruleContext);
    }
}