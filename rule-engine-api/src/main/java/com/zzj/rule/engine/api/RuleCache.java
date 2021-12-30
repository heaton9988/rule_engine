package com.zzj.rule.engine.api;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

/**
 * @author : zhuhongying@bytedance.com
 * @since : 2020/10/23
 */
@Slf4j
public class RuleCache {

    private RuleCache() {}

    private static Cache<String, Optional<Object>> localMemoryCache =
        CacheBuilder.newBuilder().expireAfterWrite(180, TimeUnit.DAYS).build();

    public static String GROOVY_SHELL_KEY_PREFIX = "GROOVY_SHELL#";

    public static <T> T getValue(String key, Callable<Optional<Object>> load) {
        try {
            Optional<Object> value = localMemoryCache.get(key, load);
            return (T) value.orElse(null);
        } catch (Exception ex) {
//            log.error("获取缓存异常,key:{} ", key, ex);
        }
        return null;
    }

}
