package com.zzj.rule.server.config;

import com.google.common.collect.Maps;
import com.googlecode.aviator.AviatorEvaluator;
import com.zzj.rule.server.function.BaseFunction;

import java.util.Map;

public class GeneralFxManager {
    private static Map<String, BaseFunction> name2function = Maps.newHashMap();

    public static BaseFunction getFxBy(String fxName) {
        return name2function.get(fxName);
    }

    public static void registerFunction(BaseFunction fxCommon) {
        AviatorEvaluator.addFunction(fxCommon);
        name2function.put(fxCommon.getName(), fxCommon);
    }
}