package com.zzj.rule.engine.api.utils;

/**
 * @author : zhuhongying@bytedance.com
 * @since : 2021/5/12
 */
public class BooleanUtils {

    public static boolean isTrue(Boolean bool) {
        return bool != null && bool;
    }

    public static boolean isFalse(Boolean bool) {
        return bool != null && !bool;
    }

    public static boolean isFalseOrNull(Boolean bool) {
        return bool == null || !bool;
    }

}
