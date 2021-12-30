package com.zzj.rule.engine.api.script;

/**
 * @author : zhuhongying@bytedance.com
 * @since : 2021/4/6
 */
public class ImportCodeNode extends AstNode {

    public ImportCodeNode(Class<?> cls) {
        super(cls.getName());
    }

    public ImportCodeNode(String clsName) {
        super(clsName);
    }

    @Override
    public String toScript() {
        return "import " + getName() + ";";
    }

    @Override
    public String toScriptLog() {
        return toScript();
    }

}
