package com.zzj.rule.engine.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zzj.rule.engine.server.entity.RiskCtrlRuleDetail;

import java.util.Map;

/**
 * <p>
 * 风险控制规则-明细表 服务类
 * </p>
 *
 * @author heaton9988
 * @since 2021-12-02
 */
public interface RiskCtrlRuleService extends IService<RiskCtrlRuleDetail> {
//    String CACHE_CHECK_RESULT = "form-web:riskCheckResult:"; // + formHeaderId:checkResult(true通过,false有强风险)
//
//    String genCheckResultCacheKey(InvocationInfo invocationInfo, Long formHeaderId);
//
//    List<Map<String, Object>> queryRuleHeaderList(InvocationInfo invocationInfo, String bizClsId, String orgCategory, String triggerCode);
//
//    RiskCtrlCheckResultEnum checkByFormPayDetailId(InvocationInfo invocationInfo, Long formPayDetailId, RiskCtrlTriggerEnum triggerEnum);
//
//    RiskCtrlCheckResultEnum checkByFormHeaderId(InvocationInfo invocationInfo, Long formHeaderId, RiskCtrlTriggerEnum triggerEnum);
//
//    RiskCtrlCheckResultEnum checkByFormHeader(InvocationInfo invocationInfo, FormHeader formHeader, RiskCtrlTriggerEnum triggerEnum);

    //    RiskCtrlCheckResultEnum executeExpression(InvocationInfo invocationInfo, FormHeaderRuleBo formHeaderRuleBo, String orgCategory, RiskCtrlTriggerEnum triggerEnum);
    Map<String, Object> executeExpression(Map<String, Object> inputMap);

//    Set<Long> queryTravellingProcessInstanceIds(InvocationInfo invocationInfo, List<Long> formHeaderIds) throws TException;
}