package com.biology.domain.manage.nongDuData.db;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

// import io.lettuce.core.dynamic.annotation.Param;
import org.apache.ibatis.annotations.Param;

public interface NongDuDataMapper extends BaseMapper<NongDuDataEntity> {

        @Select("SELECT DATE_FORMAT(m.minute, '%Y-%m-%d %H:%i') AS minute," +
                        "CASE WHEN COUNT(d.working_status) > 0 THEN 1 ELSE 0 END AS is_online " +
                        "FROM ( " +
                        "  SELECT TIMESTAMP(#{startTime}) + INTERVAL (a.a + b.a*10 + c.a*100 + d.a*1000) MINUTE AS minute "
                        +
                        "  FROM (SELECT 0 a UNION SELECT 1 UNION SELECT 2 UNION SELECT 3 UNION SELECT 4 " +
                        "        UNION SELECT 5 UNION SELECT 6 UNION SELECT 7 UNION SELECT 8 UNION SELECT 9) a, " +
                        "       (SELECT 0 a UNION SELECT 1 UNION SELECT 2 UNION SELECT 3 UNION SELECT 4 " +
                        "        UNION SELECT 5 UNION SELECT 6 UNION SELECT 7 UNION SELECT 8 UNION SELECT 9) b, " +
                        "       (SELECT 0 a UNION SELECT 1 UNION SELECT 2 UNION SELECT 3 UNION SELECT 4 " +
                        "        UNION SELECT 5 UNION SELECT 6 UNION SELECT 7 UNION SELECT 8 UNION SELECT 9) c, " +
                        "       (SELECT 0 a UNION SELECT 1) d " +
                        ") m " +
                        "LEFT JOIN manage_nong_du_data d ON d.device_sn = #{deviceSn} " +
                        " AND d.create_time >= m.minute " +
                        " AND d.create_time < m.minute + INTERVAL 1 MINUTE " +
                        "WHERE m.minute BETWEEN #{startTime} AND #{endTime} " +
                        "GROUP BY m.minute " +
                        "ORDER BY m.minute")
        public List<Map<String, Object>> selectIsOnlineHistory(@Param("deviceSn") String deviceSn,
                        @Param("startTime") String startTime, @Param("endTime") String endTime);

        @Select("SELECT DATE_FORMAT(m.minute, '%Y-%m-%d %H:%i') AS minute," +
                        "CASE WHEN COUNT(d.working_status) > 0 THEN 1 ELSE 0 END AS is_online " +
                        "FROM ( " +
                        "  SELECT TIMESTAMP(#{startTime}) + INTERVAL (a.a + b.a*10 + c.a*100 + d.a*1000) MINUTE AS minute "
                        +
                        "  FROM (SELECT 0 a UNION SELECT 1 UNION SELECT 2 UNION SELECT 3 UNION SELECT 4 " +
                        "        UNION SELECT 5 UNION SELECT 6 UNION SELECT 7 UNION SELECT 8 UNION SELECT 9) a, " +
                        "       (SELECT 0 a UNION SELECT 1 UNION SELECT 2 UNION SELECT 3 UNION SELECT 4 " +
                        "        UNION SELECT 5 UNION SELECT 6 UNION SELECT 7 UNION SELECT 8 UNION SELECT 9) b, " +
                        "       (SELECT 0 a UNION SELECT 1 UNION SELECT 2 UNION SELECT 3 UNION SELECT 4 " +
                        "        UNION SELECT 5 UNION SELECT 6 UNION SELECT 7 UNION SELECT 8 UNION SELECT 9) c, " +
                        "       (SELECT 0 a UNION SELECT 1) d " +
                        ") m " +
                        "LEFT JOIN manage_nong_du_data d ON d.device_sn is null " +
                        " AND d.create_time >= m.minute " +
                        " AND d.create_time < m.minute + INTERVAL 1 MINUTE " +
                        "WHERE m.minute BETWEEN #{startTime} AND #{endTime} " +
                        "GROUP BY m.minute " +
                        "ORDER BY m.minute")
        public List<Map<String, Object>> selectIsOnlineHistoryIsNull(
                        @Param("startTime") String startTime, @Param("endTime") String endTime);

        // 统计时间内的报警数量
        @Select("SELECT COUNT(nong_du_data_id) FROM manage_nong_du_data WHERE create_time BETWEEN #{startTime} AND #{endTime} AND alarm = 1 AND device_sn = #{deviceSn}")
        public Integer getAlarmCount(@Param("startTime") String startTime, @Param("endTime") String endTime,
                        @Param("deviceSn") String deviceSn);

        // 今日报警数量
        @Select({
                        "SELECT COUNT(nong_du_data_id) FROM manage_nong_du_data WHERE DATE(create_time) = CURDATE() AND alarm = 1"
        })
        public Integer getJinRiBaoJing();
}
