/**
 * *****************************************************
 * Copyright (C) 2021 bytedance.com. All Rights Reserved
 * This file is part of bytedance EA project.
 * Unauthorized copy of this file, via any medium is strictly prohibited.
 * Proprietary and Confidential.
 * ****************************************************
 **/
package com.zzj.rule.engine.server.common.util;

import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * List 转换工具类
 *
 * @author shanchaonan<shanchaonan @ bytedance.com>
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
