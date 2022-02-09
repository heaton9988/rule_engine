package com.zzj.rule.engine.api.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 */
public class RuleUtils {

    public static boolean equalsDay(LocalDateTime date1, LocalDateTime date2) {
        if (date1 == date2) {
            return true;
        }
        if (date1 == null || date2 == null) {
            return false;
        }
        return date1.toLocalDate().toEpochDay() == date2.toLocalDate().toEpochDay();
    }

    /**
     * 判断是否超出当前日期.
     */
    public static boolean isAfterToday(LocalDateTime dateTime) {
        if (dateTime == null) {
            return false;
        }
        return dateTime.toLocalDate().isAfter(LocalDate.now());
    }

    /**
     * 判断是否在某日期之后.
     */
    public static boolean isAfter(LocalDateTime dateTime, LocalDateTime compareTo) {
        if (compareTo == null) {
            return false;
        }
        if (dateTime == null) {
            return false;
        }
        return dateTime.toLocalDate().isAfter(compareTo.toLocalDate());
    }

    public static boolean isAfter(LocalDate dateTime, LocalDate compareTo) {
        if (compareTo == null) {
            return false;
        }
        if (dateTime == null) {
            return false;
        }
        return dateTime.isAfter(compareTo);
    }

    /**
     * 判断是否在某日期之前.
     */
    public static boolean isBefore(LocalDateTime dateTime, LocalDateTime compareTo) {
        if (compareTo == null) {
            return false;
        }
        if (dateTime == null) {
            return false;
        }
        return dateTime.toLocalDate().isBefore(compareTo.toLocalDate());
    }

    public static <T> boolean hasIntersection(List<T> a, List<T> b) {
        if (a == null || b == null) {
            return false;
        }
        if (a.size() > b.size()) {
            return hasIntersection(b, a);
        }
        Set<T> aSet = new HashSet<>(a);
        return b.stream().anyMatch(aSet::contains);
    }
}
