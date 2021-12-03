/**
 * *****************************************************
 * Copyright (C) 2021 bytedance.com. All Rights Reserved
 * This file is part of bytedance EA project.
 * Unauthorized copy of this file, via any medium is strictly prohibited.
 * Proprietary and Confidential.
 * ****************************************************
 */
package com.zzj.rule.server.common.util;

import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

/**
 * <p>时间工具类</p>
 *
 * @author rongchen<rongchen @ bytedance.com>
 * @date 02/08/2021
 */
@Slf4j
public class TimeUtils {

    public static LocalDateTime now() {
        return LocalDateTime.now();
    }

    /**
     * 日期格式字符串
     */
    public static final String YMD_HYPHEN_HMS_COLO = "yyyy-MM-dd HH:mm:ss";
    public static final String YMD_HYPHEN = "yyyy-MM-dd";
    public static final String MDY_HYPHEN = "MM-dd-yyyy";
    public static final String YMD_SLASH = "yyyy/MM/dd";
    public static final String MDY_SLASH = "MM/dd/yyyy";
    public static final String YMD_DOT = "yyyy.MM.dd";
    public static final String DMY_DOT = "dd.MM.yyyy";
    public static final String YMD_HMS = "yyyyMMddHHmmss";

    /**
     * 日期格式formatter
     */
    public static final DateTimeFormatter YMD_HYPHEN_HMS_COLO_DTF = DateTimeFormatter.ofPattern(YMD_HYPHEN_HMS_COLO);
    public static final DateTimeFormatter YMD_HYPHEN_DTF = DateTimeFormatter.ofPattern(YMD_HYPHEN);
    public static final DateTimeFormatter MDY_HYPHEN_DTF = DateTimeFormatter.ofPattern(MDY_HYPHEN);
    public static final DateTimeFormatter YMD_SLASH_DTF = DateTimeFormatter.ofPattern(YMD_SLASH);
    public static final DateTimeFormatter MDY_SLASH_DTF = DateTimeFormatter.ofPattern(MDY_SLASH);
    public static final DateTimeFormatter YMD_DOT_DTF = DateTimeFormatter.ofPattern(YMD_DOT);
    public static final DateTimeFormatter DMY_DOT_DTF = DateTimeFormatter.ofPattern(DMY_DOT);
    public static final DateTimeFormatter YMD_HMS_DTF = DateTimeFormatter.ofPattern(YMD_HMS);

    // 2. “时间”格式需要支持12小时或24小时制
    // 3. 所有在页面中由填写人录入的日期，包括发票日期、付款日期等，不需要进行多时区转换；
    // 由于系统自动产生的时间戳：如单据创建时间、单据修改时间等，在前端显示时需要考虑时区转换

    public static String format(LocalDateTime dateTime, DateTimeFormatter formatter) {
        try {
            return dateTime.format(formatter);
        } catch (Exception e) {
            return "";
        }
    }

    public static LocalDateTime buildDefaultDateTime(String dateTime) {
        try {
            return LocalDateTime.parse(dateTime, YMD_HYPHEN_HMS_COLO_DTF);
        } catch (Exception e) {
            return null;
        }
    }


    public static LocalDateTime buildDateTime(String dateTime, DateTimeFormatter formatter) {
        try {
            return LocalDateTime.parse(dateTime, formatter);
        } catch (Exception e) {
            return null;
        }
    }

    public static String localDateTimeToString(LocalDateTime localDateTime) {
        try {
            return localDateTime.format(YMD_HYPHEN_HMS_COLO_DTF);
        } catch (Exception e) {
            return "";
        }

    }

    public static String dateToStr(Date date) {
        return dateToStr(date, YMD_HYPHEN);
    }

    public static Date strToDate(String dateStr) {
        return strToDate(dateStr, YMD_HYPHEN);
    }

    public static Date strToDate(String dateStr, String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        try {
            return dateFormat.parse(dateStr);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public static String dateToStr(Date date, String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(date);
    }

    public static long toTimestamp(LocalDateTime localDateTime) {
        return toTimestampByZoneId(localDateTime, ZoneId.systemDefault());
    }

    public static long toTimestamp(String dateTime) {
        return toTimestampByZoneId(buildDefaultDateTime(dateTime), ZoneId.systemDefault());
    }

    public static long toTimestampByZoneId(LocalDateTime localDateTime, ZoneId zoneId) {
        Instant instant = localDateTime.atZone(zoneId).toInstant();
        return instant.toEpochMilli();
    }

    public static long toTimestampByZoneId(String dateTime, DateTimeFormatter formatter, ZoneId zoneId) {
        Instant instant = buildDateTime(dateTime, formatter).atZone(zoneId).toInstant();
        return instant.toEpochMilli();
    }

    /**
     * 根据间隔天数获取日期(时分秒为0)
     *
     * @param inteval
     * @return
     */
    public static Date getTimeByAfterDays(int inteval) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.add(Calendar.DATE, inteval);
        Date date = calendar.getTime();
        return date;
    }


}
