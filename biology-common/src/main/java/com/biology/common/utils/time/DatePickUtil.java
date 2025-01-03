package com.biology.common.utils.time;

import cn.hutool.core.date.DateUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

/**
 * @author valarchie
 */
@Slf4j
public class DatePickUtil {

    private DatePickUtil() {
    }

    /**
     * 安全地获取日期的一天开始时间, date为null 则返回null DateUtil.beginOfDay(date) 如果传null 会NPE
     *
     * @param date 当前日期
     * @return 日期的一天开始时间
     */
    public static Date getBeginOfTheDay(Date date) {
        if (date == null) {
            return null;
        }
        try {
            return DateUtil.beginOfDay(date);
        } catch (Exception e) {
            log.error("pick begin of the day failed, due to: ", e);
        }
        return null;
    }

    /**
     * 安全地获取日期的一天结束时间, date为null 则返回null。 避免NPE
     * DateUtil.endOfDay(date) 如果传null 会NPE
     * 
     * @param date 23:59:59
     * @return 日期的一天结束时间
     */
    public static Date getEndOfTheDay(Date date) {
        if (date == null) {
            return null;
        }

        try {
            return DateUtil.endOfDay(date);
        } catch (Exception e) {
            log.error("pick end of the day failed, due to: ", e);
        }
        return null;
    }

    public static List<String> getDayNow() {
        // 获取0:00到现在的时间
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startOfDay = now.toLocalDate().atStartOfDay();
        List<String> times = new ArrayList<>();

        // 遍历每个小时
        LocalDateTime currentTime = startOfDay;
        while (currentTime.isBefore(now)) {
            // 获取当前时间的小时部分并格式化
            times.add(currentTime.format(DateTimeFormatter.ofPattern("HH:mm")));
            currentTime = currentTime.plusHours(1);
        }
        return times;
    }

    public static List<String> getWeekNowMMDD() {
        LocalDate today = LocalDate.now();
        List<String> dates = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd");
        // 从今天开始，往前推 7 天
        for (int i = 0; i < 7; i++) {
            String formattedDate = today.minusDays(6 - i).format(formatter);
            dates.add(formattedDate);
        }

        return dates;
    }

    public static List<String> getWeekNowYYMMDD() {
        LocalDate today = LocalDate.now();
        List<String> dates = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("YYYY-MM-dd");
        // 从今天开始，往前推 7 天
        for (int i = 0; i < 7; i++) {
            String formattedDate = today.minusDays(6 - i).format(formatter);
            dates.add(formattedDate);
        }

        return dates;
    }

    public static List<String> getMonthNowMMDD() {
        LocalDate today = LocalDate.now();

        // 获取上个月的今天
        LocalDate lastMonthToday = today.minusMonths(1);
        List<String> dates = new ArrayList<>();

        // 格式化器：MM-dd
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd");

        // 从 startDate 到 endDate 遍历日期
        LocalDate currentDate = lastMonthToday;
        while (!currentDate.isAfter(today)) {
            // 将日期格式化为 MM-dd
            String formattedDate = currentDate.format(formatter);
            dates.add(formattedDate);
            currentDate = currentDate.plusDays(1); // 增加一天
        }

        return dates;
    }

    public static List<String> getYearNow() {
        List<String> result = new ArrayList<>();
        LocalDate now = LocalDate.now();
        int year = now.getYear();
        // 获取今年的年份
        int currentYear = now.getYear();
        // 获取当前月份
        int currentMonth = now.getMonthValue();
        // 格式化器：MM
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM");
        // 从1月到当前月份遍历并格式化
        for (int month = 1; month <= currentMonth; month++) {
            // 构建当前月份的日期（使用年份和月份）
            LocalDate date = LocalDate.of(currentYear, month, 1);
            // 将月份格式化为 MM
            result.add(date.format(formatter));
        }
        return result;

    }

    public static List<String> getYearNowBefore() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");

        // 获取当前月份
        LocalDate currentDate = LocalDate.now();

        List<String> months = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            months.add(currentDate.format(formatter));
            // 向前推一个月
            currentDate = currentDate.minusMonths(1);
        }
        Collections.reverse(months);
        return months;
    }

    // 获取本周的日期
    public static List<String> getWeekNow() {
        LocalDate today = LocalDate.now();
        List<String> dates = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd");
        // 从本周的开始日期开始，往后推 7 天
        for (int i = 0; i < 7; i++) {
            String formattedDate = today.minusDays(6 - i).format(formatter);
            dates.add(formattedDate);
        }

        return dates;
    }
}
