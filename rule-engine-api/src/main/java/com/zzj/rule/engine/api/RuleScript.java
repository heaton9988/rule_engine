package com.zzj.rule.engine.api;

import com.zzj.rule.engine.api.enums.Key;
import com.zzj.rule.engine.api.enums.KeyTypeEnum;
import com.zzj.rule.engine.api.enums.OperateEnum;
import com.zzj.rule.engine.api.enums.UnionEnum;
import com.zzj.rule.engine.api.enums.Value;
import lombok.Data;

import java.util.List;

/**
 * 前端规则模型.
 */
@Data
public final class RuleScript {

    /** 聚合/字段类型 and/or. */
    private UnionEnum union;

    /** 左值类型. */
    private KeyTypeEnum keyType;

    /** 左值. */
    private Key key;

    /** 操作符. */
    private OperateEnum op;

    /** 右值. */
    private Value value;

    /** 子规则. */
    private List<RuleScript> children;

    /** 规则序号 */
    private String scriptIndex = "";

    /** 开启Debug跟踪日志 */
    private Boolean isDebug = false;

    public final boolean isUnion() {
        return union != null;
    }

}
