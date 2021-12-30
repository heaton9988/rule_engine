package com.zzj.rule.engine.api;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.zzj.rule.engine.api.utils.BooleanUtils;
import com.zzj.rule.engine.api.utils.JSONUtils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;

/**
 * @author : zhuhongying@bytedance.com
 * @since : 2021/4/6
 */
@Slf4j
public class RuleTrace {

    public final Boolean trace(
        Callable<Boolean> callable,
        String ruleName,
        String ruleScript,
        Boolean isDebug,
        Map<String, Object> variables,
        String index) throws Exception {

        if (BooleanUtils.isFalseOrNull(isDebug)) {
            return callable.call();
        } else {
            try {
                Map<String, Object> originalVariableMap = deepCopy(variables);
                long startTimeInMillIs = System.currentTimeMillis();
                boolean result = callable.call();
                long costDuration = System.currentTimeMillis() - startTimeInMillIs;
                Map<String, Object> afterVariableMap = deepCopy(variables);

                String script = encodeToString(ruleScript.getBytes(StandardCharsets.UTF_8));
//                String script = new String(Base64Utils.decodeFromString(ruleScript));
                List<DiffObject> diffObjects = diffMap(originalVariableMap, afterVariableMap);
                for (DiffObject diff : diffObjects) {
                    trace(ruleName, index, costDuration, diff.toString(), true);
                }
                trace(ruleName, index, costDuration, script, result);
                return result;
            } catch (Exception e) {
//                log.warn("fail on tracing rule execution: {}, error: {}", ruleName, e.getMessage(), e);
                return callable.call();
            }
        }

    }

    private void trace(String ruleName, String index, Long costDuration, String script, boolean result) {
        int tabs = Math.max(StringUtils.countOccurrencesOf(index, "."), 1) * 4;
        String formatter = "[%s][%d ms][%s]%" + tabs + "s>%s";
        if (!result) {
            formatter = "\033[0;31m" + formatter + "\033[0m";
            log.warn(String.format(formatter, ruleName, costDuration, index, "", script));
        } else {
            formatter = "\033[0;36m" + formatter + "\033[0m";
            log.info(String.format(formatter, ruleName, costDuration, index, "", script));
        }
    }

    private Map<String, Object> deepCopy(Map<String, Object> variables) {
        Map<String, Object> map = Maps.newHashMap(variables);
        map.remove(RuleEngine.GROOVY_VARIABLES);
        return JSONUtils.deepCopy(map);
    }

    private List<DiffObject> diffMap(Map<String, Object> originVariableMap, Map<String, Object> afterVariableMap) {
        List<DiffObject> diffObjects = Lists.newArrayList();
        Set<String> keySets = Sets.newHashSet(originVariableMap.keySet());
        keySets.addAll(afterVariableMap.keySet());
        for (String key : keySets) {
            Object originValue = originVariableMap.get(key);
            Object afterValue = afterVariableMap.get(key);
            if (!eq(originValue, afterValue)) {
                VarOp op = VarOp.CHANGE;
                if (!originVariableMap.containsKey(key)) {
                    op = VarOp.CREATE;
                } else if (!afterVariableMap.containsKey(key)) {
                    op = VarOp.DELETE;
                }
                diffObjects.add(DiffObject.builder()
                    .op(op)
                    .key(key)
                    .originValue(originValue)
                    .afterValue(afterValue)
                    .build());
            }
        }
        return diffObjects;
    }

    private boolean eq(Object o1, Object o2) {
        if (o1 == o2) {
            return true;
        }
        if (o1 == null || o2 == null) {
            return false;
        }
        return o1.equals(o2);
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DiffObject {

        private VarOp op;
        private String key;
        private Object originValue;
        private Object afterValue;

        @Override
        public String toString() {
            return op.getCode() + key + " = " + afterValue;
        }

    }

    public enum VarOp {

        CREATE(" var "),
        CHANGE(" set "),
        DELETE(" del ");
        private String code;

        VarOp(String code) {
            this.code = code;
        }

        public String getCode() {
            return code;
        }

    }

    public static void main(String[] args) {
        Map<String, Object> map = Maps.newHashMap();
        map.put("a", null);
        map.put("b", 1);
        System.out.println(JSONUtils.toJSONString(map));
    }


    public static String encodeToString(byte[] src) {
        return src.length == 0 ? "" : new String(encode(src), StandardCharsets.UTF_8);
    }

    public static byte[] encode(byte[] src) {
        return src.length == 0 ? src : Base64.getEncoder().encode(src);
    }
}
