package com.zzj.rule.engine.api.transformer;

import com.google.common.collect.Lists;
import com.zzj.rule.engine.api.RuleContext;
import com.zzj.rule.engine.api.RuleScript;
import com.zzj.rule.engine.api.RuleTrace;
import com.zzj.rule.engine.api.script.BodyCodeNode;
import com.zzj.rule.engine.api.script.ImportCodeNode;
import com.zzj.rule.engine.api.script.MainCodeNode;
import com.zzj.rule.engine.api.utils.RuleUtils;
import org.apache.commons.collections.CollectionUtils;

import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;

/**
 * @author : zhuhongying@bytedance.com
 * @since : 2021/4/6
 */
public class ScriptTransformer {

    public MainCodeNode transform(RuleContext ruleContext) {
        Set<String> importClasses = ruleContext.getImportClasses();
        importClasses.add(Callable.class.getName());
        importClasses.add(RuleTrace.class.getName());
        importClasses.add(RuleUtils.class.getName());
        importClasses.add("java.time.*");
        importClasses.add("java.math.*");
        importClasses.add("java.util.*");

        List<ImportCodeNode> importCodeNodes = Lists.newArrayList();
        if (!CollectionUtils.isEmpty(importClasses)) {
            for (String importClass : importClasses) {
                importCodeNodes.add(new ImportCodeNode(importClass));
            }
        }
        RuleScript script = ruleContext.getRuleScript();
        BodyCodeNode codeNode = BodyCodeNode.generateCodeByRule(ruleContext, null, script, 1);
        return MainCodeNode.builder()
            .importNodes(importCodeNodes)
            .returnNodes(codeNode)
            .build();
    }
}