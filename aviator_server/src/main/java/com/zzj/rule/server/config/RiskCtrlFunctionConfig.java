package com.zzj.rule.server.config;

import com.google.common.collect.Maps;
import com.googlecode.aviator.AviatorEvaluator;
import com.zzj.rule.server.function.FxContractLinkageExceedsDownpayment;
import com.zzj.rule.server.function.RiskCtrlFunctionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import javax.annotation.PostConstruct;
import java.util.Map;

public class RiskCtrlFunctionConfig {
    @Autowired
    ApplicationContext applicationContext;

    private Map<String, RiskCtrlFunctionService> name2fx = Maps.newHashMap();

    public RiskCtrlFunctionService getByFxName(String fxName) {
        return name2fx.get(fxName);
    }

    @PostConstruct
    public void registerFunction() {
        {
            FxContractLinkageExceedsDownpayment bean = applicationContext.getBean(FxContractLinkageExceedsDownpayment.class);
            AviatorEvaluator.addFunction(bean);
            name2fx.put(bean.getName(), bean);
        }
    }
}