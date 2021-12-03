/**
 * *****************************************************
 * Copyright (C) 2021 bytedance.com. All Rights Reserved
 * This file is part of bytedance EA project.
 * Unauthorized copy of this file, via any medium is strictly prohibited.
 * Proprietary and Confidential.
 * ****************************************************
 */
package com.zzj.rule.server.function;

import com.google.common.collect.Lists;
import com.googlecode.aviator.runtime.function.AbstractFunction;
import com.googlecode.aviator.runtime.type.AviatorObject;
import com.googlecode.aviator.runtime.type.AviatorString;
import com.zzj.rule.server.api.FxResult;
import com.zzj.rule.server.common.util.JSONUtil;
import com.zzj.rule.server.config.GeneralFxManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public abstract class BaseFunction extends AbstractFunction {
    public static final String IS_SUCCESS = "isSucc";

    @PostConstruct
    public void init() {
        // register to General Function Manager
        GeneralFxManager.registerFunction(this);
    }

    RiskCtrlFunctionService riskCtrlFxService;

    @Override
    public AviatorObject call(Map<String, Object> fxInputParamMap) {
        try {
            if (Objects.equals(fxInputParamMap.get(IS_SUCCESS), true)) {
                return new AviatorString(JSONUtil.toJson(FxResult.builder().isSucc(true).childResultList(Lists.newArrayList()).build()));
            }

            FxResult executeResult = riskCtrlFxService.execute(fxInputParamMap);
            return new AviatorString(JSONUtil.toJson(executeResult));
        } catch (Exception e) {
            log.error(getName(), e);
            return new AviatorString(JSONUtil.toJson(FxResult.builder().isSucc(false).childResultList(Lists.newArrayList()).build()));
        }
    }

    public String null2empty(String str) {
        if (str == null) return "";
        return str;
    }
}