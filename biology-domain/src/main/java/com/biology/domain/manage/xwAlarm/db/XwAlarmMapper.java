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

    @Select("WITH RECURSIVE time_slots AS (\n" + //
            "    SELECT STR_TO_DATE(CONCAT(CURDATE(), ' 00:00:00'), '%Y-%m-%d %H:%i:%s') AS slot_start\n" + //
            "    UNION ALL\n" + //
            "    SELECT slot_start + INTERVAL 30 MINUTE\n" + //
            "    FROM time_slots\n" + //
            "    WHERE slot_start + INTERVAL 30 MINUTE < STR_TO_DATE(CONCAT(CURDATE(), ' 23:59:59'), '%Y-%m-%d %H:%i:%s')\n"
            + //
            ")"
            + " SELECT \n" + //
            "    DATE_FORMAT(ts.slot_start, '%H:%i') as name,\n" + //
            "    COUNT(a.create_time) AS value\n" + //
            "FROM time_slots ts\n" + //
            "LEFT JOIN manage_xw_alarm a\n" + //
            "    ON a.create_time >= ts.slot_start\n" + //
            "   AND a.create_time < ts.slot_start + INTERVAL 30 MINUTE\n" + //
            " WHERE a.seat_number like '#{seatNumber}\n'" +
            "GROUP BY ts.slot_start\n" + //
            "ORDER BY ts.slot_start;")
    List<PingGuJieGuoSeriesDTO> jiWeiQuShiBianHuaByDay(@Param("seatNumber") String seatNumber);

    @Select("WITH RECURSIVE time_slots AS (\n" + //
            "    SELECT STR_TO_DATE(CONCAT(CURDATE(), ' 00:00:00'), '%Y-%m-%d %H:%i:%s') AS slot_start\n" + //
            "    UNION ALL\n" + //
            "    SELECT slot_start + INTERVAL 30 MINUTE\n" + //
            "    FROM time_slots\n" + //
            "    WHERE slot_start + INTERVAL 30 MINUTE < STR_TO_DATE(CONCAT(CURDATE(), ' 23:59:59'), '%Y-%m-%d %H:%i:%s')\n"
            + //
            ")"
            + " SELECT \n" + //
            "    DATE_FORMAT(ts.slot_start, '%H:%i') as name,\n" + //
            "    COUNT(a.create_time) AS value\n" + //
            "FROM time_slots ts\n" + //
            "LEFT JOIN manage_xw_alarm a\n" + //
            "    ON a.create_time >= ts.slot_start\n" + //
            "   AND a.create_time < ts.slot_start + INTERVAL 30 MINUTE\n" + //
            "GROUP BY ts.slot_start\n" + //
            "ORDER BY ts.slot_start;")
    List<PingGuJieGuoSeriesDTO> jiWeiQuShiBianHuaByDayAll();

    // @Select("select COUNT(*) as value,seat_number as name from manage_xw_alarm
    // where YEARWEEK(create_time, 1) = YEARWEEK(CURDATE(), 1)"
    // + " group by seat_number")
    @Select("WITH RECURSIVE week_days AS (\n" + //
            "    -- 本周周一\n" + //
            "    SELECT DATE_SUB(CURDATE(), INTERVAL WEEKDAY(CURDATE()) DAY) AS day_date\n" + //
            "    UNION ALL\n" + //
            "    SELECT day_date + INTERVAL 1 DAY\n" + //
            "    FROM week_days\n" + //
            "    WHERE day_date + INTERVAL 1 DAY <= DATE_SUB(CURDATE(), INTERVAL WEEKDAY(CURDATE()) DAY) + INTERVAL 6 DAY\n"
            + //
            ")\n" + //
            "SELECT \n" + //
            "    wd.day_date as name,\n" + //
            "    COUNT(a.create_time) AS value\n" + //
            "FROM week_days wd\n" + //
            "LEFT JOIN manage_xw_alarm a\n" + //
            "    ON DATE(a.create_time) = wd.day_date\n" + //
            " WHERE a.seat_number like '#{seatNumber}' " +
            "GROUP BY wd.day_date\n" + //
            "ORDER BY wd.day_date;")
    List<PingGuJieGuoSeriesDTO> jiWeiQuShiBianHuaByWeek(@Param("seatNumber") String seatNumber);

    @Select("WITH RECURSIVE week_days AS (\n" + //
            "    -- 本周周一\n" + //
            "    SELECT DATE_SUB(CURDATE(), INTERVAL WEEKDAY(CURDATE()) DAY) AS day_date\n" + //
            "    UNION ALL\n" + //
            "    SELECT day_date + INTERVAL 1 DAY\n" + //
            "    FROM week_days\n" + //
            "    WHERE day_date + INTERVAL 1 DAY <= DATE_SUB(CURDATE(), INTERVAL WEEKDAY(CURDATE()) DAY) + INTERVAL 6 DAY\n"
            + //
            ")\n" + //
            "SELECT \n" + //
            "    wd.day_date as name,\n" + //
            "    COUNT(a.create_time) AS value\n" + //
            "FROM week_days wd\n" + //
            "LEFT JOIN manage_xw_alarm a\n" + //
            "    ON DATE(a.create_time) = wd.day_date\n" + //
            "GROUP BY wd.day_date\n" + //
            "ORDER BY wd.day_date;")
    List<PingGuJieGuoSeriesDTO> jiWeiQuShiBianHuaByWeekAll();

    // @Select("select COUNT(*) as value,seat_number as name from manage_xw_alarm
    // where YEAR(create_time) = YEAR(CURDATE()) AND MONTH(create_time) =
    // MONTH(CURDATE())"
    // + " group by seat_number")
    @Select("WITH RECURSIVE month_days AS (\n" + //
            "    SELECT DATE_FORMAT(CURDATE(), '%Y-%m-01') AS day_date  -- 本月第一天\n" + //
            "    UNION ALL\n" + //
            "    SELECT day_date + INTERVAL 1 DAY\n" + //
            "    FROM month_days\n" + //
            "    WHERE day_date + INTERVAL 1 DAY <= LAST_DAY(CURDATE())  -- 本月最后一天\n" + //
            ")\n" + //
            "SELECT \n" + //
            "    DAY(md.day_date) AS name,      -- 返回 1,2,3...\n" + //
            "    COUNT(a.create_time) AS value\n" + //
            "FROM month_days md\n" + //
            "LEFT JOIN manage_xw_alarm a\n" + //
            "    ON DATE(a.create_time) = md.day_date\n" + //
            " WHERE a.seat_number like '#{seatNumber}' " +
            "GROUP BY md.day_date\n" + //
            "ORDER BY md.day_date;")
    List<PingGuJieGuoSeriesDTO> jiWeiQuShiBianHuaByMonth(@Param("seatNumber") String seatNumber);

    @Select("WITH RECURSIVE month_days AS (\n" + //
            "    SELECT DATE_FORMAT(CURDATE(), '%Y-%m-01') AS day_date  -- 本月第一天\n" + //
            "    UNION ALL\n" + //
            "    SELECT day_date + INTERVAL 1 DAY\n" + //
            "    FROM month_days\n" + //
            "    WHERE day_date + INTERVAL 1 DAY <= LAST_DAY(CURDATE())  -- 本月最后一天\n" + //
            ")\n" + //
            "SELECT \n" + //
            "    DAY(md.day_date) AS name,      -- 返回 1,2,3...\n" + //
            "    COUNT(a.create_time) AS value\n" + //
            "FROM month_days md\n" + //
            "LEFT JOIN manage_xw_alarm a\n" + //
            "    ON DATE(a.create_time) = md.day_date\n" + //
            "GROUP BY md.day_date\n" + //
            "ORDER BY md.day_date;")
    List<PingGuJieGuoSeriesDTO> jiWeiQuShiBianHuaByMonthAll();

    // @Select("select COUNT(*) as value,seat_number as name from manage_xw_alarm
    // where YEAR(create_time) = YEAR(CURDATE())"
    // + " group by seat_number")
    @Select("WITH RECURSIVE months AS (\n" + //
            "    SELECT 1 AS month_num\n" + //
            "    UNION ALL\n" + //
            "    SELECT month_num + 1\n" + //
            "    FROM months\n" + //
            "    WHERE month_num < 12\n" + //
            ")\n" + //
            "SELECT \n" + //
            "    m.month_num as name,\n" + //
            "    COUNT(a.create_time) AS value\n" + //
            "FROM months m\n" + //
            "LEFT JOIN manage_xw_alarm a\n" + //
            "    ON MONTH(a.create_time) = m.month_num\n" + //
            "   AND YEAR(a.create_time) = YEAR(CURDATE())\n" + //
            " WHERE a.seat_number like '#{seatNumber}' " +
            "GROUP BY m.month_num\n" + //
            "ORDER BY m.month_num;")
    List<PingGuJieGuoSeriesDTO> jiWeiQuShiBianHuaByYear(@Param("seatNumber") String seatNumber);

    @Select("WITH RECURSIVE months AS (\n" + //
            "    SELECT 1 AS month_num\n" + //
            "    UNION ALL\n" + //
            "    SELECT month_num + 1\n" + //
            "    FROM months\n" + //
            "    WHERE month_num < 12\n" + //
            ")\n" + //
            "SELECT \n" + //
            "    m.month_num as name,\n" + //
            "    COUNT(a.create_time) AS value\n" + //
            "FROM months m\n" + //
            "LEFT JOIN manage_xw_alarm a\n" + //
            "    ON MONTH(a.create_time) = m.month_num\n" + //
            "   AND YEAR(a.create_time) = YEAR(CURDATE())\n" + //
            "GROUP BY m.month_num\n" + //
            "ORDER BY m.month_num;")
    List<PingGuJieGuoSeriesDTO> jiWeiQuShiBianHuaByYearAll();

}
