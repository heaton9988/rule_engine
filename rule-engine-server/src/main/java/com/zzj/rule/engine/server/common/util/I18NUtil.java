package com.zzj.rule.engine.server.common.util;

import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

/**
 * @date : 2021/06/18 11:42 上午
 * @description:
 */
@Slf4j
public final class I18NUtil {
    private static Map<String, String> langType2locale = Maps.newHashMap();

    static {
        langType2locale.put("zh-CN", "zh");
        langType2locale.put("en-US", "en");
        langType2locale.put("ja-JP", "ja");
    }

    public static String getLocaleByLangType(String langType) {
        String locale = langType2locale.get(langType);
        if (StringUtils.isBlank(locale)) log.error("locale:{} when langType:{}", locale, langType);
        return locale == null ? "" : locale;
    }

    public static String getTransTextFromJson(String json, String langType) {
        if (StringUtils.isBlank(json)) return "";
        Map<String, String> locale2name = JSONUtil.fromJson(json, Map.class);
        String locale = I18NUtil.getLocaleByLangType(langType);
        if (StringUtils.isBlank(locale)) return "";
        String name = locale2name.get(locale);
        if (StringUtils.isBlank(name)) return "";
        return name;
    }
}