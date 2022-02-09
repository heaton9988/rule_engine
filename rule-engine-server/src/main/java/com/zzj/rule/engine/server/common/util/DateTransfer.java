package com.zzj.rule.engine.server.common.util;

import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.util.Date;
import java.util.TimeZone;

/**
 * <p>date和 localDate转换</p>
 *
 * @date 04/22/2021
 */
@Slf4j
public class DateTransfer {

    /**
     * 时区 和jdbc链接数据库的时区一致
     */
    private static final String DEFAULT_ZONE_ID = "Asia/Shanghai";

    /**
     * localDate 转 date
     *
     * @param localDate
     * @return
     */
    public static Date localDateToDate(LocalDate localDate) {
        ZoneId zone = ZoneId.of(DEFAULT_ZONE_ID);
        Instant instant = localDate.atStartOfDay().atZone(zone).toInstant();
        Date date = Date.from(instant);
        return date;
    }

    /**
     * localDateTime 转 date
     *
     * @param localDate
     * @return
     */
    public static Date localDateTimeToDate(LocalDateTime localDate) {
        ZoneId zone = ZoneId.of(DEFAULT_ZONE_ID);
        ZonedDateTime zdt = localDate.atZone(zone);
        Date date = Date.from(zdt.toInstant());
        return date;
    }

    /**
     * date 转 localDate
     *
     * @param date
     * @return
     */
    public static LocalDate dateToLocalDate(Date date) {
        Instant instant = date.toInstant();
        ZoneId zone = ZoneId.of(DEFAULT_ZONE_ID);
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zone);
        LocalDate localDate = localDateTime.toLocalDate();
        return localDate;
    }

    /**
     * date 转 localDateTime
     *
     * @param date
     * @return
     */
    public static LocalDateTime dateToLocalDateTime(Date date) {
        Instant instant = date.toInstant();
        ZoneId zone = ZoneId.of(DEFAULT_ZONE_ID);
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zone);
        return localDateTime;
    }

    /**
     * 按照北京时区
     * 将 yyyy-MM-dd HH:mm:ss 格式的时间 转成Date
     *
     * @param dateStr
     * @return
     */
    public static Date dateStrToDate(String dateStr) throws ParseException {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sf.setTimeZone(TimeZone.getTimeZone(DEFAULT_ZONE_ID));
        return sf.parse(dateStr);
    }

//    test
//    public static void main(String[] args) {
//        Date date = new Date();
//        System.out.println("-----------------"+date.toString());
//        LocalDateTime localDateTime = dateToLocalDateTime(date);
//        System.out.println("-----------------"+localDateTime.toString());
//        Date date2 = localDateTimeToDate(localDateTime);
//        System.out.println("-----------------"+date2.toString());
//    }

}
