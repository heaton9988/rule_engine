
package com.zzj.rule.engine.server.common.util;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * @date 05/09/2021
 **/
public class PredicateUtil {

    /**
     * 用于filter中，根据给定的key对列表进行去重
     *
     * @param keyExtractor keyExtractor
     * @param <T>          对象
     * @return predicate
     */
    public static <T> Predicate<T> distinctByKey(Function<? super T, Object> keyExtractor) {
        Map<Object, Boolean> map = new ConcurrentHashMap<>();
        return t -> map.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }
}