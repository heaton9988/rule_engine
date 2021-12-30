package com.zzj.rule.engine.api.loader;

import com.google.common.collect.Lists;
import com.google.common.io.Files;
import com.zzj.rule.engine.api.Rule;
import com.zzj.rule.engine.api.utils.JSONUtils;
import lombok.SneakyThrows;

import java.io.File;
import java.nio.charset.Charset;
import java.util.List;

/**
 * @author : zhuhongying@bytedance.com
 * @since : 2021/4/6
 */
public class ClassPathRuleLoader implements RuleLoader {
    private String classpath;

    public ClassPathRuleLoader(String classpath) {
        this.classpath = classpath;
    }

    @SneakyThrows
    @Override
    public List<Rule> loadRules(String triggerEvent, File dir) {
        List<Rule> rules = Lists.newArrayList();
        for (File file : dir.listFiles()) {
            String name = file.getName();
            if (!name.endsWith("json")) continue;

            List<String> lines = Files.readLines(file, Charset.defaultCharset());
            StringBuilder sb = new StringBuilder();
            for (String line : lines) {
                sb.append(line);
            }
            Rule rule = JSONUtils.parseObject(sb.toString(), Rule.class);
            if (rule.getTriggerEvent().equalsIgnoreCase(triggerEvent)) {
                rules.add(rule);
            }
        }

        return rules;
    }

    @Override
    public List<Rule> loadRules(String tenantId, String triggerEvent) {
        throw new UnsupportedOperationException("Please use loadRules(String triggerEvent, File dir)");
    }
}