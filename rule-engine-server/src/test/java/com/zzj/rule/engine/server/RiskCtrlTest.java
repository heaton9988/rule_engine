package com.zzj.rule.engine.server;

import com.zzj.rule.engine.server.entity.RiskCtrlRuleHeader;
import com.zzj.rule.engine.server.mapper.RiskCtrlRuleHeaderMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest(classes = RuleEngineServerApplication.class)
@RunWith(SpringRunner.class)
public class RiskCtrlTest {
    @Resource
    RiskCtrlRuleHeaderMapper riskCtrlRuleHeaderMapper;

    @Test
    public void a() {
        List<RiskCtrlRuleHeader> riskCtrlRuleHeaders = riskCtrlRuleHeaderMapper.selectList(null);
        for (RiskCtrlRuleHeader ruleHeader : riskCtrlRuleHeaders) {

        }
        System.out.println(123);
    }

//    @Test
//    public void executeExpression() {
//        Integer riskLevel=-1;// 无任何风险
//
//
//
//        // 1. 拿到 1， 3， 4 id of risk_rule_header_id   我这里拿  6（事前申请自己的）， 3 ， 4
//        List<Map<String, Object>> riskRuleHeaderList = queryRuleHeaderList(invocationInfo, bizClsId, orgCategory, triggerEnum.getCode());
//
//        log.info("bizClsId=" + bizClsId + ",orgCategory=" + orgCategory + ",trigger=" + triggerEnum.getCode() + ",formHeaderId=" + formHeaderId + ",riskRuleHeaderList=" + riskRuleHeaderList);
//
//        if (riskRuleHeaderList == null) { // 没有正确拿到对应的规则
//            throw new CustomException(FormErrorEnum.RISK_CTRL_INVOKE_CONFIG_THRIFT_FAIL);
//        } else if (riskRuleHeaderList.size() == 0) { // 正确的拿到了对应的规则, 但是租户没配规则, 并且平台规则也没配
//            log.warn("riskRuleHeaderList is null: bizClsId=" + bizClsId + ",orgCategory=" + orgCategory + ",trigger=" + triggerEnum.getCode());
//        }
//
//        // 根据ruleIdList清理之前生成的风控信息
//        Set<Long> riskRuleIdSet = Sets.newHashSet();
//        riskRuleIdSet.addAll(riskRuleHeaderList.stream().filter(k -> k != null).map(ruleHeader -> ((Double) ruleHeader.get("Id")).longValue()).collect(Collectors.toSet())); // 对公配的id
//        riskRuleIdSet.addAll(functionName2ruleCtx.values().stream().filter(k -> k != null).map(k -> Long.parseLong(k.getId())).collect(Collectors.toList()));// 对私配置的id
//        formExceptionDetailModifyService.preDel(invocationInfo, triggerEnum, formHeaderId, Lists.newArrayList(riskRuleIdSet));
//
//        Long ruleId = null; // 规则头表id ex: ruleId = 1
//        String functionName = null; // functionName = aviatorExpression = FxVendorDownpaymentItemReminder
//        String expressionType = null; // 表达式类型 expressionType=Function
//        List<Map<String, String>> dataList = Lists.newArrayList();
//
//        for (String fxName : functionName2ruleCtx.keySet()) { // 报销侧配置的风控规则
//            RuleContext ruleContext = functionName2ruleCtx.get(fxName);
//            ruleId = Long.parseLong(ruleContext.getId());
//            functionName = ruleContext.getRuleName();
//            expressionType = ruleContext.getGroup();
//
//            Map<String, Object> env = prepareEnv(invocationInfo, formHeaderRuleBo, triggerEnum);
//
//            if (Objects.equals(expressionType, RiskCtrlExpressionTypeEnum.Function.getCode())) {
//                ruleContext.setVariables(env);
//                List<RuleCommand> commands = ruleEngine.execute(ruleContext); // json里的结果为true, 会输出非空的commands
//                if (CollectionUtils.isNotEmpty(commands)) {
//                    log.info("commands:{}", commands.toString());
//                    RuleCommand ruleCommand = commands.get(0);
//                    String[] args = ruleCommand.getArgs();
//
//                    Map<String, String> data = Maps.newHashMap();
//                    if (ArrayUtils.isNotEmpty(args)) {
//                        for (int i = 0; i < args.length; i = i + 2) {
//                            if (i + 1 >= args.length) {
//                                continue;
//                            }
//                            data.put(args[i], args[i + 1]);
//                        }
//                    }
//                    dataList.add(data);
//
//                    String parentTemplate = data.remove("parentTemplateLangKey");
//
//                    String template = data.remove("childTemplateLangKey");
//
//                    String ruleDesc = data.remove("locale2name");
//                    String ruleName = I18NUtil.getTransTextFromJson(ruleDesc, InvocationInfoProxy.getLanguage());
//
//                    int maxCtrlLevel = Integer.parseInt(data.remove("ctrlLevel"));
//
//                    String parentComponentCode = data.remove("parentComponentCode"); // 父-组件
//                    if (StringUtils.isBlank(parentComponentCode)) {
//                        parentComponentCode = "";
//                    }
//
//                    String componentCode = data.remove("componentCode"); // 组件
//                    if (StringUtils.isBlank(componentCode)) {
//                        componentCode = "";
//                    }
//
//                    Long parentComponentDetailId = 0L;
//                    String strParentComponentDetailId = data.remove("parentComponentDetailId"); // 父-组件对应的明细行id
//                    if (StringUtils.isNotBlank(strParentComponentDetailId)) {
//                        parentComponentDetailId = new BigDecimal(strParentComponentDetailId).longValue();
//                    }
//
//                    Long componentDetailId = 0L;
//                    String strComponentDetailId = data.remove("componentDetailId"); // 组件对应的明细行id
//                    if (StringUtils.isNotBlank(strComponentDetailId)) {
//                        componentDetailId = new BigDecimal(strComponentDetailId).longValue();
//                    }
//
//                    String showColumnField = data.remove("showColumnField");
//                    if (StringUtils.isBlank(showColumnField)) {
//                        showColumnField = "";
//                    }
//
//                    String templateParamValue = JSONUtil.toJson(data);
//                    formExceptionDetailModifyService.saveMsg(invocationInfo, formHeaderId, parentComponentCode, parentComponentDetailId, componentCode, componentDetailId, showColumnField, ruleId, ruleName, "", ruleDesc, maxCtrlLevel, "", parentTemplate, "", template, templateParamValue);
//
//                    checkResultEnum = RiskCtrlCheckResultEnum.newLevel(checkResultEnum, maxCtrlLevel); // 如果新的level比原先的等级高, 就升级到新level对应的等级
//                }
//            }
//        }
//
//        for (Map<String, Object> ruleHeader : riskRuleHeaderList) { // 遍历每个自定义函数
//            ruleId = ((Double) ruleHeader.get("Id")).longValue(); // 规则头表id
//            functionName = (String) ruleHeader.get("AviatorExpression"); // 根据aviator规则生成的表达式 aviatorExpression=FxVendorDownpaymentItemReminder
//            expressionType = (String) ruleHeader.get("ExpressionType"); //表达式类型 expressionType=Function
//
//            String showColumnField = (String) ruleHeader.get("ShowColumnField"); //展示在哪个字段上 showColumnField
//            String ruleName = (String) ruleHeader.get("Name"); // 规则名 ruleName = 核销预付提醒
//            String ruleNameLangKey = (String) ruleHeader.get("DescLangKey"); // 规则名的语言码
//
//            if (functionName2ruleCtx.keySet().contains(functionName)) { // 如果对私的已经配置了, 优先跑对私的逻辑
//                continue;
//            }
//
//            String parentTemplateLangKey = (String) ruleHeader.get("TemplateLangKey"); // 父-风控模板的语言码
//            if (StringUtils.isBlank(parentTemplateLangKey)) {
//                parentTemplateLangKey = "";
//            }
//
//            String childTemplateLangKey = (String) ruleHeader.get("ChildTemplateLangKey"); // 风控模板的语言码
//            if (StringUtils.isBlank(childTemplateLangKey)) {
//                childTemplateLangKey = "";
//            }
//
//            Integer maxCtrlLevel = ((Double) ruleHeader.get("CtrlLevel")).intValue(); // 风控等级
//
//            Map<String, Object> env = prepareEnv(invocationInfo, formHeaderRuleBo, triggerEnum);
//
//            if (Objects.equals(expressionType, RiskCtrlExpressionTypeEnum.Function.getCode())) {
//                RiskCtrlFunctionService fxService = riskCtrlFunctionConfig.getByFxName(functionName);
//                if (fxService == null) {
//                    log.error("CANNOT find fxService by aviatorExpression:{}", functionName);
//                    continue;
//                }
//                // 此时只有  理解成为  函数的 Input Params
//                log.info("fxService-Name:{}", functionName);
//
//                fxService.prepareEnv(invocationInfo, env);
//
//                // 这里的 execute()方法会调用 call()，理解成为函数的执行
//                String aviatorResultStr = (String) AviatorEvaluator.execute(functionName + "()", env); // 执行表达式, 返回校验结果
//                log.info("formHeaderId={}, aviatorResultStr={}", formHeaderId, aviatorResultStr);
//
//                // 此处为函数的 output
//                Map<String, Object> resultMap = JSONUtil.fromJson(aviatorResultStr, Map.class);
//
//                dataList = (List<Map<String, String>>) resultMap.get("dataList");
//
//                // 统一处理dataList, 并记入form_exception_detail表
//                for (Map<String, String> data : dataList) {
//                    String specificParentTemplateLangKey = data.remove("ParentTemplateLangKey");
//                    if (StringUtils.isNotBlank(specificParentTemplateLangKey)) {
//                        parentTemplateLangKey = specificParentTemplateLangKey;
//                    }
//
//                    String specificChildTemplateLangKey = data.remove("ChildTemplateLangKey");
//                    if (StringUtils.isNotBlank(specificChildTemplateLangKey)) {
//                        childTemplateLangKey = specificChildTemplateLangKey;
//                    }
//
//                    String specificDescLangKey = data.remove("DescLangKey");
//                    if (StringUtils.isNotBlank(specificDescLangKey)) {
//                        ruleNameLangKey = specificDescLangKey;
//                    }
//
//                    String specificCtrlLevel = data.remove("ctrlLevel");
//                    if (StringUtils.isNotBlank(specificCtrlLevel)) {
//                        Integer iCtrlLevel = Integer.parseInt(specificCtrlLevel);
//                        if (iCtrlLevel > maxCtrlLevel) {
//                            maxCtrlLevel = iCtrlLevel;
//                        }
//                    }
//
//                    String parentComponentCode = data.remove("parentComponentCode"); // 父-组件
//                    if (StringUtils.isBlank(parentComponentCode)) {
//                        parentComponentCode = "";
//                    }
//
//                    String componentCode = data.remove("componentCode"); // 组件
//                    if (StringUtils.isBlank(componentCode)) {
//                        componentCode = "";
//                    }
//
//                    Long parentComponentDetailId = 0L;
//                    String strParentComponentDetailId = data.remove("parentComponentDetailId"); // 父-组件对应的明细行id
//                    if (StringUtils.isNotBlank(strParentComponentDetailId)) {
//                        parentComponentDetailId = new BigDecimal(strParentComponentDetailId).longValue();
//                    }
//
//                    Long componentDetailId = 0L;
//                    String strComponentDetailId = data.remove("componentDetailId"); // 组件对应的明细行id
//                    if (StringUtils.isNotBlank(strComponentDetailId)) {
//                        componentDetailId = new BigDecimal(strComponentDetailId).longValue();
//                    }
//
//                    String templateParamValue = JSONUtil.toJson(data);
//                    formExceptionDetailModifyService.saveMsg(invocationInfo, formHeaderId, parentComponentCode, parentComponentDetailId, componentCode, componentDetailId, showColumnField, ruleId, ruleName, ruleNameLangKey, null, maxCtrlLevel, parentTemplateLangKey, null, childTemplateLangKey, null, templateParamValue);
//                }
//
//                boolean singleRuleIsFail = !(Boolean) resultMap.get("isSucc");
//                if (singleRuleIsFail) {
//                    checkResultEnum = RiskCtrlCheckResultEnum.newLevel(checkResultEnum, maxCtrlLevel); // 如果新的level比原先的等级高, 就升级到新level对应的等级
//                }
//            }
//        }
//        return checkResultEnum;
//    }

    @Test
    public void testSingleForm() {
//        long tenantId = 0L;
//        Result<DetectAndSubmitResp> loan = riskCtrlController.detectAndSubmit(7021444684066521382L, "LOAN");
//        System.out.println();
    }
}