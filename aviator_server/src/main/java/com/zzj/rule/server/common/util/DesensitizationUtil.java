/**
 * *****************************************************
 * Copyright (C) 2021 bytedance.com. All Rights Reserved
 * This file is part of bytedance EA project.
 * Unauthorized copy of this file, via any medium is strictly prohibited.
 * Proprietary and Confidential.
 * ****************************************************
 **/
package com.zzj.rule.server.common.util;

import org.springframework.util.StringUtils;

import java.util.Collections;

/**
 * 脱敏工具类
 *
 * @author zhaoanka<zhaoanka @ bytedance.com>
 * @date 03/24/2021
 **/
public class DesensitizationUtil {

    private static final int BANK_ACCOUNT_DESENSE_DIGITS = 4;

    private static final int BANK_ACCOUNT_MID_START_LEN = 4;

    /**
     * 脱敏，保留前几位和后几位，中间脱敏展示
     *
     * @param text  需要脱敏的text
     * @param first 前几位
     * @param last  后几位
     * @return 脱敏后的字符
     */
    public static String desensitization(String text, int first, int last) {
        if (StringUtils.isEmpty(text)) {
            return text;
        }
        int starLen = text.length() - first - last;
        if (starLen <= 0) {
            return text;
        }
        String textFirst = text.substring(0, first);
        String textLast = text.substring(text.length() - last);
        String stars = String.join("", Collections.nCopies(starLen, "*"));
        return textFirst + stars + textLast;
    }

    /**
     * 根据传入文本，取前first位，后last位，中间加midStarLen形成新的脱敏字符串
     *
     * @param text       文本
     * @param first      前几位
     * @param last       后几位
     * @param midStarLen 中间*长度
     * @return
     */
    public static String desensitization(String text, int first, int last, int midStarLen) {
        if (StringUtils.isEmpty(text)) {
            return text;
        }
        // 最大只截取到字符串长度
        if (first > text.length()) {
            first = text.length();
        }
        if (last > text.length()) {
            last = text.length();
        }
        String textFirst = text.substring(0, first);
        String textLast = text.substring(text.length() - last);
        String stars = String.join("", Collections.nCopies(midStarLen, "*"));
        return textFirst + stars + textLast;
    }

    /**
     * 托敏银行账户
     *
     * @param bankAccount 需要脱敏的bankAccount
     * @return 脱敏银行账户
     */
    public static String bankAccountDesensitization(String bankAccount) {
        return desensitization(bankAccount, BANK_ACCOUNT_DESENSE_DIGITS,
                BANK_ACCOUNT_DESENSE_DIGITS, BANK_ACCOUNT_MID_START_LEN);
    }
}
