/**
 * *****************************************************
 * Copyright (C) 2021 bytedance.com. All Rights Reserved
 * This file is part of bytedance EA project.
 * Unauthorized copy of this file, via any medium is strictly prohibited.
 * Proprietary and Confidential.
 * ****************************************************
 **/

package com.zzj.rule.engine.server.common.util;

import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;

/**
 * @author liuzhifu<liuzhifu.deaco @ bytedance.com>
 * @date 06/08/2021 12:04 下午
 */
public class NullCheckUtils {
    public static int NON_ZERO_DECIMAL = 0;

    public static int check(Object param) {
        if (param == null) {
            return 1;
        }
        if (param instanceof String) {
            if (StringUtils.isNotEmpty((String) param)) {
                return 0;
            } else {
                return 1;
            }
        }
        return 0;
    }

    public static int check(Object param, int cmd) {
        if (param == null) {
            return 1;
        }
        if (param instanceof String) {
            if (StringUtils.isNotEmpty((String) param)) {
                return 0;
            } else {
                return 1;
            }
        }
        if ((param instanceof BigDecimal) && cmd == NON_ZERO_DECIMAL) {
            if (MoneyUtils.isPositive((BigDecimal) param)) {
                return 0;
            } else {
                return 1;
            }
        }
        return 0;
    }
}
