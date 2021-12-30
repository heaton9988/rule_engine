package com.zzj.rule.engine.api.script;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.zzj.rule.engine.api.RuleContext;
import com.zzj.rule.engine.api.RuleScript;
import com.zzj.rule.engine.api.enums.UnionEnum;
import org.apache.commons.collections.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * 聚合节点.
 */
public class UnionCodeNode extends BodyCodeNode {

    private List<BodyCodeNode> childNodes;

    private static Map<UnionEnum, String> typeEnumMap = Maps.newHashMap();

    static {
        typeEnumMap.put(UnionEnum.AND, " && ");
        typeEnumMap.put(UnionEnum.OR, " || ");
    }

    public UnionCodeNode(RuleContext ruleContext, RuleScript rule) {
        super(typeEnumMap.get(rule.getUnion()));
        List<BodyCodeNode> childNodes = Lists.newArrayList();
        if (!CollectionUtils.isEmpty(rule.getChildren())) {
            AtomicInteger index = new AtomicInteger(0);
            for (RuleScript ruleChild : rule.getChildren()) {
                childNodes.add(generateCodeByRule(ruleContext, rule, ruleChild,
                        index.incrementAndGet()));
            }
        }
        this.childNodes = childNodes;
    }

    @Override
    public String toScript() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(" (");
        stringBuilder.append(childNodes.stream().map(AstNode::toScript)
                .collect(Collectors.joining(getName())));
        stringBuilder.append(" )");
        return stringBuilder.toString();
    }

    @Override
    public String toScriptLog() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(" (");
        stringBuilder.append(childNodes.stream().map(AstNode::toScriptLog)
                .collect(Collectors.joining(getName())));
        stringBuilder.append(" )");
        return stringBuilder.toString();
    }

}
