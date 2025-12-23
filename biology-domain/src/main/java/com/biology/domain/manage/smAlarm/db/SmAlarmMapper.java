package com.biology.domain.manage.smAlarm.db;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.biology.domain.manage.shiJuan.dto.PingGuJieGuoSeriesDTO;

import io.lettuce.core.dynamic.annotation.Param;

public interface SmAlarmMapper extends BaseMapper<SmAlarmEntity> {

        @Select("select COUNT(*) as value,type as name from manage_sm_alarm group by device_sn,type")
        List<PingGuJieGuoSeriesDTO> getLiShiYiChangPaiMing();

        @Select("WITH RECURSIVE dates AS (\n" + //
                        "    SELECT CURDATE() AS dt\n" + //
                        "    UNION ALL\n" + //
                        "    SELECT dt - INTERVAL 1 DAY\n" + //
                        "    FROM dates\n" + //
                        "    WHERE dt > CURDATE() - INTERVAL 6 DAY\n" + //
                        ")\n" + //
                        "SELECT \n" + //
                        "    DATE_FORMAT(d.dt, '%m-%d') AS name,     -- 只显示 月-日\n" + //
                        "    COALESCE(COUNT(m.sm_alarm_id), 0) AS value\n" + //
                        "FROM dates d\n" + //
                        "LEFT JOIN manage_sm_alarm m \n" + //
                        "    ON DATE(m.create_time) = d.dt\n" + //
                        "GROUP BY d.dt\n" + //
                        "ORDER BY d.dt;\n" + //
                        "")
        List<PingGuJieGuoSeriesDTO> getBaoJingCiShuTongJiByRecentWeek();

        @Select({
                        "SELECT COUNT(*) from manage_sm_alarm where DATE(create_time) = CURDATE()"
        })
        Integer getJinRiAlarmNum();

        @Select({
                        "SELECT COUNT(*) from manage_xw_alarm where DATE(create_time) = CURDATE() and flag = 1"
        })
        Integer getJinRiXwAlarmNum();

        // 统计今日报警的数量
        @Select({
                        "SELECT COUNT(sm_alarm_id) from manage_sm_alarm where DATE(create_time) = CURDATE()"
        })
        Integer getTodayAlarmNum();
}
