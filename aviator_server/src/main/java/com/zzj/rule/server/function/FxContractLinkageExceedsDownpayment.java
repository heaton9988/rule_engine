package com.zzj.rule.server.function;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.zzj.rule.server.api.FxResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * - 校验程序方法逻辑：需要检查该合同关联的支付明细行金额是否超出目前该合同支付明细行的剩余可用金额（合同行支付总金额-合同行已付总金额-合同在途付款总金额）
 * - 触发方式：item_save、pay_save、report_submit
 * - 校验程序消息：#{vendorName} 合同#{exceedsAmountContractNameList}关联金额超出上限
 */
@Service
@Slf4j
public class FxContractLinkageExceedsDownpayment extends FxCommon implements RiskCtrlFunctionService {
    @Override
    public String getName() {
        return "FxContractLinkageExceedsDownpayment";
    }

    @Override
    public void prepareEnv(Map<String, Object> env) throws Exception {
        try {
            String root = (String) env.get("root");
            env.put("root", "prepared " + root);
        } catch (Exception e) {
            log.error(getName(), e);
            throw new Exception("RISK_CTRL_PREPARE_ENV_FAIL", e);
        }
    }

    @Override
    public FxResult execute(Map<String, Object> fxInputParamMap) throws Exception {
        FxResult rootResult = FxResult.builder().isSucc(true).childResultList(Lists.newArrayList()).build();

        try {
            Map<String, Object> data = Maps.newHashMap();

//                1. 风控组件头：【供应商名称】【合同名称】关联金额超出上限
//                2. 中间（支付卡片）：tag 事件 预付合同关联金额超额
//                3. 右抽屉-已关联合同组件：关联金额超出上限
            data.put("ChildTemplateLangKey", "risk_ctrl_fx_template_contractlinkageexceeds_downpayment_child");

            rootResult.getChildResultList().add(FxResult.builder().isSucc(false).build());

            // 如果风控校验不通过, 设isSucc=false
            rootResult.setIsSucc(false);
        } catch (Exception e) {
            log.error(getName(), e);
            rootResult.setIsSucc(false);
        }
        return rootResult;
    }
}