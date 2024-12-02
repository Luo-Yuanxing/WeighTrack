package io.github.weightrack.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;

public class DateUtil {

    // 获取今天的开始时间（00:00:00）
    public static LocalDateTime getTodayStartTime() {
        return LocalDate.now().atStartOfDay();
    }

    // 获取今天的结束时间（23:59:59）
    public static LocalDateTime getTodayEndTime() {
        return LocalDate.now().atTime(23, 59, 59);
    }

    // 获取本月第一天
    public static LocalDateTime getFirstDayOfMonth() {
        return LocalDate.now().withDayOfMonth(1).atStartOfDay();
    }

    // 获取本月最后一天
    public static LocalDateTime getLastDayOfMonth() {
        return LocalDate.now().with(TemporalAdjusters.lastDayOfMonth()).atTime(23, 59, 59);
    }

    // 获取本年第一天
    public static LocalDateTime getFirstDayOfYear() {
        return LocalDate.now().withDayOfYear(1).atStartOfDay();
    }

    // 获取本年最后一天
    public static LocalDateTime getLastDayOfYear() {
        return LocalDate.now().with(TemporalAdjusters.lastDayOfYear()).atTime(23, 59, 59);
    }

    public static LocalDateTime getAllStartTime() {
        return LocalDateTime.of(1970, 1, 1, 0, 0, 0);
    }

    public static LocalDateTime getAllEndTime() {
        return LocalDateTime.of(2200, 1, 1, 0, 0, 0);
    }
}
