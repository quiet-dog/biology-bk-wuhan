package com.biology.domain.manage.xwAlarm.db;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.biology.domain.manage.shiJuan.dto.PingGuJieGuoSeriesDTO;

import io.lettuce.core.dynamic.annotation.Param;

public interface XwAlarmMapper extends BaseMapper<XwAlarmEntity> {

        @Select("select COUNT(*) as value,seat_number as name from manage_xw_alarm where YEAR(create_time) = YEAR(CURDATE()) AND WEEK(create_time, 1) = WEEK(CURDATE(), 1)"
                        + " group by seat_number")
        List<PingGuJieGuoSeriesDTO> JiWeiBaoJingZhanBiByWeek(@Param("dayType") String dayType);

        @Select("select COUNT(*) as value,seat_number as name from manage_xw_alarm where YEAR(create_time) = YEAR(CURDATE())\n"
                        + //
                        "  AND MONTH(create_time) = MONTH(CURDATE())"
                        + " group by seat_number")
        List<PingGuJieGuoSeriesDTO> JiWeiBaoJingZhanBiByMonth(@Param("dayType") String dayType);

        @Select("select COUNT(*) as value,seat_number as name from manage_xw_alarm where YEAR(create_time) = YEAR(CURDATE())"
                        + " group by seat_number")
        List<PingGuJieGuoSeriesDTO> JiWeiBaoJingZhanBiByYear(@Param("dayType") String dayType);

        @Select("WITH RECURSIVE months AS ("
                        + " SELECT 1 AS month"
                        + " UNION ALL"
                        + " SELECT month + 1 FROM months WHERE month < 12"
                        + " )"
                        + " SELECT"
                        + " m.month as name,"
                        + " COALESCE(COUNT(a.xw_alarm_id), 0) AS value"
                        + " FROM months m"
                        + " LEFT JOIN manage_xw_alarm a"
                        + " ON MONTH(a.create_time) = m.month"
                        + " AND YEAR(a.create_time) = YEAR(CURDATE())"
                        + " GROUP BY m.month"
                        + " ORDER BY m.month")
        List<PingGuJieGuoSeriesDTO> JianCeShuJuTongJi();

        @Select("WITH RECURSIVE time_slots AS ( " +
                        "SELECT STR_TO_DATE(CONCAT(CURDATE(), ' 00:00:00'), '%Y-%m-%d %H:%i:%s') AS slot_start " +
                        "UNION ALL " +
                        "SELECT slot_start + INTERVAL 30 MINUTE FROM time_slots " +
                        "WHERE slot_start + INTERVAL 30 MINUTE < STR_TO_DATE(CONCAT(CURDATE(), ' 23:59:59'), '%Y-%m-%d %H:%i:%s') "
                        +
                        ") " +
                        "SELECT DATE_FORMAT(ts.slot_start, '%H:%i') as name, COUNT(a.create_time) AS value " +
                        "FROM time_slots ts " +
                        "LEFT JOIN manage_xw_alarm a " +
                        "ON a.create_time >= ts.slot_start AND a.create_time < ts.slot_start + INTERVAL 30 MINUTE " +
                        "AND a.seat_number LIKE concat('%', #{seatNumber}, '%') " +
                        "GROUP BY ts.slot_start " +
                        "ORDER BY ts.slot_start;")
        List<PingGuJieGuoSeriesDTO> jiWeiQuShiBianHuaByDay(@Param("seatNumber") String seatNumber);

        @Select("WITH RECURSIVE time_slots AS ( " +
                        "SELECT STR_TO_DATE(CONCAT(CURDATE(), ' 00:00:00'), '%Y-%m-%d %H:%i:%s') AS slot_start " +
                        "UNION ALL " +
                        "SELECT slot_start + INTERVAL 30 MINUTE FROM time_slots " +
                        "WHERE slot_start + INTERVAL 30 MINUTE < STR_TO_DATE(CONCAT(CURDATE(), ' 23:59:59'), '%Y-%m-%d %H:%i:%s') "
                        +
                        ") " +
                        "SELECT DATE_FORMAT(ts.slot_start, '%H:%i') as name, COUNT(a.create_time) AS value " +
                        "FROM time_slots ts " +
                        "LEFT JOIN manage_xw_alarm a " +
                        "ON a.create_time >= ts.slot_start AND a.create_time < ts.slot_start + INTERVAL 30 MINUTE " +
                        "GROUP BY ts.slot_start " +
                        "ORDER BY ts.slot_start;")
        List<PingGuJieGuoSeriesDTO> jiWeiQuShiBianHuaByDayAll();

        // @Select("select COUNT(*) as value,seat_number as name from manage_xw_alarm
        // where YEARWEEK(create_time, 1) = YEARWEEK(CURDATE(), 1)"
        // + " group by seat_number")
        @Select("WITH RECURSIVE week_days AS ( " +
                        "SELECT DATE_SUB(CURDATE(), INTERVAL WEEKDAY(CURDATE()) DAY) AS day_date " +
                        "UNION ALL " +
                        "SELECT day_date + INTERVAL 1 DAY FROM week_days " +
                        "WHERE day_date + INTERVAL 1 DAY <= DATE_SUB(CURDATE(), INTERVAL WEEKDAY(CURDATE()) DAY) + INTERVAL 6 DAY "
                        +
                        ") " +
                        "SELECT wd.day_date as name, COUNT(a.create_time) AS value " +
                        "FROM week_days wd " +
                        "LEFT JOIN manage_xw_alarm a " +
                        "ON DATE(a.create_time) = wd.day_date " +
                        "AND a.seat_number LIKE concat('%', #{seatNumber}, '%') " +
                        "GROUP BY wd.day_date " +
                        "ORDER BY wd.day_date;")
        List<PingGuJieGuoSeriesDTO> jiWeiQuShiBianHuaByWeek(@Param("seatNumber") String seatNumber);

        @Select("WITH RECURSIVE week_days AS ( " +
                        "SELECT DATE_SUB(CURDATE(), INTERVAL WEEKDAY(CURDATE()) DAY) AS day_date " +
                        "UNION ALL " +
                        "SELECT day_date + INTERVAL 1 DAY FROM week_days " +
                        "WHERE day_date + INTERVAL 1 DAY <= DATE_SUB(CURDATE(), INTERVAL WEEKDAY(CURDATE()) DAY) + INTERVAL 6 DAY "
                        +
                        ") " +
                        "SELECT wd.day_date as name, COUNT(a.create_time) AS value " +
                        "FROM week_days wd " +
                        "LEFT JOIN manage_xw_alarm a " +
                        "ON DATE(a.create_time) = wd.day_date " +
                        "GROUP BY wd.day_date " +
                        "ORDER BY wd.day_date;")
        List<PingGuJieGuoSeriesDTO> jiWeiQuShiBianHuaByWeekAll();

        // @Select("select COUNT(*) as value,seat_number as name from manage_xw_alarm
        // where YEAR(create_time) = YEAR(CURDATE()) AND MONTH(create_time) =
        // MONTH(CURDATE())"
        // + " group by seat_number")
        @Select("WITH RECURSIVE month_days AS ( " +
                        "SELECT DATE_FORMAT(CURDATE(), '%Y-%m-01') AS day_date " + // 本月第一天
                        "UNION ALL " +
                        "SELECT day_date + INTERVAL 1 DAY FROM month_days " +
                        "WHERE day_date + INTERVAL 1 DAY <= LAST_DAY(CURDATE()) " + // 本月最后一天
                        ") " +
                        "SELECT DAY(md.day_date) AS name, COUNT(a.create_time) AS value " +
                        "FROM month_days md " +
                        "LEFT JOIN manage_xw_alarm a " +
                        "ON DATE(a.create_time) = md.day_date " +
                        "AND a.seat_number LIKE concat('%', #{seatNumber}, '%') " + // ✅ 条件放在 JOIN 中
                        "GROUP BY md.day_date " +
                        "ORDER BY md.day_date;")
        List<PingGuJieGuoSeriesDTO> jiWeiQuShiBianHuaByMonth(@Param("seatNumber") String seatNumber);

        @Select("WITH RECURSIVE month_days AS ( " +
                        "SELECT DATE_FORMAT(CURDATE(), '%Y-%m-01') AS day_date " + // 本月第一天
                        "UNION ALL " +
                        "SELECT day_date + INTERVAL 1 DAY FROM month_days " +
                        "WHERE day_date + INTERVAL 1 DAY <= LAST_DAY(CURDATE()) " + // 本月最后一天
                        ") " +
                        "SELECT DAY(md.day_date) AS name, COUNT(a.create_time) AS value " +
                        "FROM month_days md " +
                        "LEFT JOIN manage_xw_alarm a " +
                        "ON DATE(a.create_time) = md.day_date " +
                        "GROUP BY md.day_date " +
                        "ORDER BY md.day_date;")
        List<PingGuJieGuoSeriesDTO> jiWeiQuShiBianHuaByMonthAll();

        // @Select("select COUNT(*) as value,seat_number as name from manage_xw_alarm
        // where YEAR(create_time) = YEAR(CURDATE())"
        // + " group by seat_number")
        @Select("WITH RECURSIVE months AS ( " +
                        "SELECT 1 AS month_num " +
                        "UNION ALL " +
                        "SELECT month_num + 1 FROM months " +
                        "WHERE month_num < 12 " +
                        ") " +
                        "SELECT m.month_num as name, COUNT(a.create_time) AS value " +
                        "FROM months m " +
                        "LEFT JOIN manage_xw_alarm a " +
                        "ON MONTH(a.create_time) = m.month_num AND YEAR(a.create_time) = YEAR(CURDATE()) " +
                        "AND a.seat_number LIKE concat('%', #{seatNumber}, '%') " + // ✅ 条件放在 JOIN 中
                        "GROUP BY m.month_num " +
                        "ORDER BY m.month_num;")
        List<PingGuJieGuoSeriesDTO> jiWeiQuShiBianHuaByYear(@Param("seatNumber") String seatNumber);

        @Select("WITH RECURSIVE months AS ( " +
                        "SELECT 1 AS month_num " +
                        "UNION ALL " +
                        "SELECT month_num + 1 FROM months " +
                        "WHERE month_num < 12 " +
                        ") " +
                        "SELECT m.month_num as name, COUNT(a.create_time) AS value " +
                        "FROM months m " +
                        "LEFT JOIN manage_xw_alarm a " +
                        "ON MONTH(a.create_time) = m.month_num AND YEAR(a.create_time) = YEAR(CURDATE()) " +
                        "GROUP BY m.month_num " +
                        "ORDER BY m.month_num;")
        List<PingGuJieGuoSeriesDTO> jiWeiQuShiBianHuaByYearAll();

}
