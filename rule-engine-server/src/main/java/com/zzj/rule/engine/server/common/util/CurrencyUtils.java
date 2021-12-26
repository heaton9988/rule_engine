/**
 * *****************************************************
 * Copyright (C) 2021 bytedance.com. All Rights Reserved
 * This file is part of bytedance EA project.
 * Unauthorized copy of this file, via any medium is strictly prohibited.
 * Proprietary and Confidential.
 * ****************************************************
 */
package com.zzj.rule.engine.server.common.util;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;

import java.util.Currency;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * 多币种(支持国际化)工具类
 *
 * @author zhouzhijun<zhouzhijun.xavier @ bytedance.com>
 * @date 2021/10/3 18:02:03
 */
public class CurrencyUtils {
    private static List<Locale> supportedLocales = Lists.newArrayList();
    private static Map<String, String> currencyCode2names = Maps.newHashMap();

    static {
        supportedLocales.add(Locale.SIMPLIFIED_CHINESE);
        supportedLocales.add(Locale.JAPAN);
        supportedLocales.add(Locale.US);

        for (Currency currency : Currency.getAvailableCurrencies()) {
            Map<String, String> locale2name = Maps.newHashMap();
            for (Locale locale : supportedLocales) {
                locale2name.put(locale.toString(), currency.getDisplayName(locale));
            }
            currencyCode2names.put(currency.getCurrencyCode(), JSONUtil.toJson(locale2name));
        }
    }

    public static String getI18NNames(String currencyCode) {
        if (StringUtils.isBlank(currencyCode)) return null;
        return currencyCode2names.get(currencyCode.toUpperCase());
    }
}