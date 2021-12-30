package com.zzj.rule.engine.api.script;

public abstract class AstNode {
    private String name;

    public AstNode(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String toScript() {
        return name;
    }

    public String toScriptLog() {return name;}
}