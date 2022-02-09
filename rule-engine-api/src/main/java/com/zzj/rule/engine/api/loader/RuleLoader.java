package com.zzj.rule.engine.api.loader;

import com.zzj.rule.engine.api.Rule;

import java.io.File;
import java.util.List;

/**
 */
public interface RuleLoader {
    List<Rule> loadRules(String triggerEvent, File dir);

    List<Rule> loadRules(String tenantId,String triggerEvent);
}