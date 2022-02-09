
package com.zzj.rule.engine.server.common.util;

import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * List 转换工具类
 *
 * @date 06/28/2021 5:21 下午
 */
public class ListTransferUtils {

    public static List<String> transferLongListToStringList(List<Long> src) {

        if (CollectionUtils.isEmpty(src)) {
            return new ArrayList<>();
        }
        List<String> list = new ArrayList<>(src.size());
        src.stream().forEach(longValue -> list.add(longValue.toString()));
        return list;
    }

    public static List<Long> transferStringListToLongList(List<String> src) {

        if (CollectionUtils.isEmpty(src)) {
            return new ArrayList<>();
        }
        List<Long> list = new ArrayList<>(src.size());
        src.stream().forEach(str -> list.add(Long.valueOf(str)));
        return list;
    }
}
