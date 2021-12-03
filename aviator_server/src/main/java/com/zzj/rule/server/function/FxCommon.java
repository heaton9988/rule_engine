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
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;

/**
 * <p>
 * 事前申请关联风险控制【仅对对公报账生效】
 * 名称：FxRequestlinkVerify
 * 校验程序方法逻辑：检查该对公报账二级分类必须关联事前申请【考虑直接通过字段状态配置实现】
 * 且报账单的按照事前申请颗粒度的汇总金额需小于事前申请的剩余申请总金额
 * 风控消息：事前申请单#{requestFormHeaderCode}关联金额超出上限
 * </p>
 *
 * @author rongchen<rongchen @ bytedance.com>
 * @date 04/06/2021
 */
@Slf4j
@Service
@RequiredArgsConstructor
public abstract class FxCommon extends AbstractFunction {
    private static final String IS_SUCCESS = "isSucc";

    RiskCtrlFunctionService riskCtrlFxService;

    public void fullfillInputMap(Map<String, Object> fxInputParamMap) throws Exception {
        try {
            if (riskCtrlFxService != null) riskCtrlFxService.prepareEnv(fxInputParamMap);
        } catch (Exception e) {
            log.error(getName(), e);
            throw new Exception("prepareEnv", e);
        }
    }

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