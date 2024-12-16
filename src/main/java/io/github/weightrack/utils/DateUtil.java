package io.github.weightrack.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;

public class DateUtil {

    static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    // 获取今天的开始时间（00:00:00）
    public static String getTodayStartTime() {
        return LocalDate.now().atStartOfDay().format(formatter);
    }

    // 获取今天的结束时间（23:59:59）
    public static String getTodayEndTime() {
        return LocalDate.now().atTime(23, 59, 59).format(formatter);
    }

    // 获取本月第一天
    public static String getFirstDayOfMonth() {
        return LocalDate.now().withDayOfMonth(1).atStartOfDay().format(formatter);
    }

    // 获取本月最后一天
    public static String getLastDayOfMonth() {
        return LocalDate.now().with(TemporalAdjusters.lastDayOfMonth()).atTime(23, 59, 59).format(formatter);
    }

    // 获取本年第一天
    public static String getFirstDayOfYear() {
        return LocalDate.now().withDayOfYear(1).atStartOfDay().format(formatter);
    }

    // 获取本年最后一天
    public static String getLastDayOfYear() {
        return LocalDate.now().with(TemporalAdjusters.lastDayOfYear()).atTime(23, 59, 59).format(formatter);
    }

    public static String getAllStartTime() {
        return LocalDateTime.of(1970, 1, 1, 0, 0, 0).format(formatter);
    }

    public static String getAllEndTime() {
        return LocalDateTime.of(2200, 1, 1, 0, 0, 0).format(formatter);
    }
}
