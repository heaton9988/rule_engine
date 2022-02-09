package com.zzj.rule.engine.api;

import com.google.common.collect.Maps;
import com.zzj.rule.engine.api.hint.I18nArgHint;
import com.zzj.rule.engine.api.loader.ClassPathRuleLoader;
import com.zzj.rule.engine.api.loader.RuleLoader;
import com.zzj.rule.engine.api.utils.JSONUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 */
@Slf4j
public class RuleTest {
    public static void main(String[] args) {
        RuleLoader ruleLoader = new ClassPathRuleLoader("/demo/*.json"); // 扫描代码路径下的规则
        List<Rule> rules = ruleLoader.loadRules("tenant.U123456.expense.save", new File("/Users/xingchuan/github/rule_engine/rule-engine-api/src/main/resources")); // 获取测试分组的规则
        RuleEngine ruleEngine = new RuleEngine(); // Spring注入一次
        ruleEngine.addHint(new I18nArgHint());
        ruleEngine.addHint(new I18nArgHint());
        ruleEngine.addHint(new I18nArgHint());
        for (Rule rule : rules) {
            RuleContext ruleContext = new RuleContext(rule);
            ruleContext.setVariables(buildParam());
            List<RuleCommand> commands = ruleEngine.execute(ruleContext);
            for (RuleCommand ruleCommand : commands) {
                System.out.println(JSONUtils.toJSONString(ruleCommand));
            }
        }
    }

    private static Map<String, Object> buildParam() {
        Map<String, Object> params = Maps.newHashMap();
        Map<String, Object> reimbursement = Maps.newHashMap();
        Map<String, Object> owner = Maps.newHashMap();
        owner.put("companyCountryCode", "CN");
        reimbursement.put("owner", owner);
        reimbursement.put("currency", "CNY");
        reimbursement.put("localCurrency", "USD");
        reimbursement.put("unionId", "U123434");
        params.put("reimbursement", reimbursement);
        return params;
    }
}