package com.zzj.rule.server.function;

import com.zzj.rule.server.api.FxResult;

import java.util.Map;

public interface RiskCtrlFunctionService {
    void prepareEnv(Map<String, Object> env) throws Exception;

    FxResult execute(Map<String, Object> fxInputParamMap) throws Exception;
}