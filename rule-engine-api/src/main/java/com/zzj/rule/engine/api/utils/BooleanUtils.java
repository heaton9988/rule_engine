package com.zzj.rule.engine.api.utils;

/**
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
