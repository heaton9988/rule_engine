package com.zzj.rule.engine.api.loader;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.zzj.rule.engine.api.Rule;
import com.zzj.rule.engine.api.utils.HttpUtils;
import com.zzj.rule.engine.api.utils.JSONUtils;
import lombok.Data;
import lombok.SneakyThrows;
import okhttp3.Request;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author : zhuhongying@bytedance.com
 * @since : 2021/4/6
 */
public class ServerRuleLoader implements RuleLoader {
    private static final ImmutableMap<String, String> REMOTE_SERVERS;

    static {
        ImmutableMap.Builder<String, String> builder = new ImmutableMap.Builder<>();
        builder.put("test", "http://localhost:8080/rule/list/?tenantId=${tenantId}&triggerEvent=${triggerEvent}");
        builder.put("pre", "http://localhost:8080/rule/list/?tenantId=${tenantId}&triggerEvent=${triggerEvent}");
        builder.put("prd", "http://localhost:8080/rule/list/?tenantId=${tenantId}&triggerEvent=${triggerEvent}");
        REMOTE_SERVERS = builder.build();
    }

    private String env;

    public ServerRuleLoader(String env) {
        this.env = env;
    }

    @SneakyThrows
    @Override
    public List<Rule> loadRules(String triggerEvent, File dir) {
        throw new UnsupportedOperationException("Please use loadRules(String tenantId, String triggerEvent)");
    }

    @SneakyThrows
    @Override
    public List<Rule> loadRules(String tenantId, String triggerEvent) {
        String remoteAPI = REMOTE_SERVERS.get(env);
        URL url = new URL(remoteAPI.replace("${triggerEvent}", triggerEvent)
                .replace("${tenantId}", tenantId));

        String domainUrl = "http://localhost:8080";
        Request request = new Request.Builder().url(domainUrl + "/spend/v1/contracts/7015582167590764838")
//                .header("Authorization", tenantAccessToken)
//                .header("X-TT-ENV", "zhouzhijun")
                .build();
        String rules = HttpUtils.get(request);
//        String rules = URLQuery.query(url, Maps.newHashMap());
        return JSONUtils.parseList(rules, RuleDTO.class).stream()
                .map(RuleDTO::getRule)
                .collect(Collectors.toList());
    }

    @Data
    public static class RuleDTO {
        private String ruleName;
        private String group;
        private Rule rule;
    }
}