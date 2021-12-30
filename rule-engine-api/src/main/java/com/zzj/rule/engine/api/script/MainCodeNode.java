package com.zzj.rule.engine.api.script;

import lombok.Builder;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author : zhuhongying@bytedance.com
 * @since : 2021/4/6
 */
public class MainCodeNode extends AstNode {

    private List<ImportCodeNode> importNodes;
    private BodyCodeNode returnNodes;

    @Builder
    public MainCodeNode(
        List<ImportCodeNode> importNodes,
        BodyCodeNode returnNodes
    ) {
        super("groovy");
        this.importNodes = importNodes;
        this.returnNodes = returnNodes;
    }

    public String toScript() {
        return importNodes.stream()
            .map(ImportCodeNode::toScript)
            .collect(Collectors.joining("\n"))
            + "\n return " + returnNodes.toScript();
    }

    @Override
    public String toScriptLog() {
        return importNodes.stream()
            .map(ImportCodeNode::toScript)
            .collect(Collectors.joining("\n"))
            + "\n return " + returnNodes.toScriptLog();
    }

    public List<ImportCodeNode> getImportNodes() {
        return importNodes;
    }

}
