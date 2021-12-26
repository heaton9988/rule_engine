package com.zzj.rule.engine.server.common.util;

import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * bean 转换通用工具类
 *
 * @author lintao.lt@bytedance.com>
 * @date 09/12/2021 6:04 下午
 */
public class BeanConvertUtils {
    /**
     * 批量convert
     *
     * @param sourceCollection
     * @param convertFunction
     * @param <R>
     * @param <T>
     * @return
     */
    public static <R, T> List<R> batchConvert(Collection<T> sourceCollection,
                                              Function<T, R> convertFunction) {
        if (CollectionUtils.isEmpty(sourceCollection) || convertFunction == null) {
            return Collections.emptyList();
        }
        return sourceCollection.stream().filter(Objects::nonNull).map(convertFunction)
                .collect(Collectors.toList());
    }
}
