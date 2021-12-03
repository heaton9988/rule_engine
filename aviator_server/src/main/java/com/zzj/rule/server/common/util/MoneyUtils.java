/**
 * *****************************************************
 * Copyright (C) 2021 bytedance.com. All Rights Reserved
 * This file is part of bytedance EA project.
 * Unauthorized copy of this file, via any medium is strictly prohibited.
 * Proprietary and Confidential.
 * ****************************************************
 */
package com.zzj.rule.server.common.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;

/**
 * <p>Money（币种，金额等）相关工具类</p>
 *
 * @author rongchen<rongchen @ bytedance.com>
 * @date 02/22/2021
 */
public class MoneyUtils {

    public static final BigDecimal ZERO_AMOUNT = new BigDecimal("0");
    public static final DecimalFormat COMMA_DIGITS_GROUPING = new DecimalFormat("#,##0.00");

    public static BigDecimal amountDiff(BigDecimal minuend, BigDecimal subtrahend) {
        return minuend.subtract(subtrahend);
    }

    public static BigDecimal amountSum(BigDecimal left, BigDecimal right) {
        return left.add(right);
    }

    public static BigDecimal totalAmount(List<BigDecimal> amounts) {
        if (amounts == null || amounts.isEmpty()) {
            return BigDecimal.ZERO;
        }
        return amounts.stream().reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public static boolean firstAmountGreater(BigDecimal first, BigDecimal second) {
        return first.compareTo(second) > 0;
    }

    public static boolean equalAmount(BigDecimal first, BigDecimal second) {
        return first.compareTo(second) == 0;
    }

    public static boolean firstAmountSmaller(BigDecimal first, BigDecimal second) {
        return first.compareTo(second) < 0;
    }

    public static boolean isNegative(BigDecimal amount) {
        return BigDecimal.ZERO.compareTo(amount) > 0;
    }

    public static boolean notPositive(BigDecimal amount) {
        return BigDecimal.ZERO.compareTo(amount) >= 0;
    }

    public static boolean isPositive(BigDecimal amount) {
        return BigDecimal.ZERO.compareTo(amount) < 0;
    }

    public static boolean notNegative(BigDecimal amount) {
        return BigDecimal.ZERO.compareTo(amount) <= 0;
    }

    public static boolean isZero(BigDecimal amount) {
        return BigDecimal.ZERO.compareTo(amount) == 0;
    }
}
