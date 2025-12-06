package com.biology.domain.manage.smData.db;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Param;

public interface SmDataMapper extends BaseMapper<SmDataEntity> {

    // 返回的时间格式为: 2025-12-01 00:00,数据格式为: {"time": "2025-12-01 00:00", "data": 100}
    // battery: 电池电压
    @Select("SELECT DATE_FORMAT(create_time, '%Y-%m-%d %H:00') AS time,AVG(battery) AS data FROM manage_sm_data WHERE sm_device_id = #{smDeviceId} AND create_time between #{startTime} and #{endTime} GROUP BY DATE_FORMAT(create_time, '%Y-%m-%d %H:00') ORDER BY DATE_FORMAT(create_time, '%Y-%m-%d %H:00')")
    public List<Map<String, Object>> selectBatteryHistory(@Param("smDeviceId") Long smDeviceId,
            @Param("startTime") String startTime, @Param("endTime") String endTime);

    // co2: co2浓度
    @Select("SELECT DATE_FORMAT(create_time, '%Y-%m-%d %H:00') AS time,AVG(co2) AS data FROM manage_sm_data WHERE sm_device_id = #{smDeviceId} AND create_time between #{startTime} and #{endTime} GROUP BY DATE_FORMAT(create_time, '%Y-%m-%d %H:00') ORDER BY DATE_FORMAT(create_time, '%Y-%m-%d %H:00')")
    public List<Map<String, Object>> selectCo2History(@Param("smDeviceId") Long smDeviceId,
            @Param("startTime") String startTime, @Param("endTime") String endTime);

    // humility: 湿度
    @Select("SELECT DATE_FORMAT(create_time, '%Y-%m-%d %H:00') AS time,AVG(humility) AS data FROM manage_sm_data WHERE sm_device_id = #{smDeviceId} AND create_time between #{startTime} and #{endTime} GROUP BY DATE_FORMAT(create_time, '%Y-%m-%d %H:00') ORDER BY DATE_FORMAT(create_time, '%Y-%m-%d %H:00')")
    public List<Map<String, Object>> selectHumilityHistory(@Param("smDeviceId") Long smDeviceId,
            @Param("startTime") String startTime, @Param("endTime") String endTime);

    // temp: 温度
    @Select("SELECT DATE_FORMAT(create_time, '%Y-%m-%d %H:00') AS time,AVG(temp) AS data FROM manage_sm_data WHERE sm_device_id = #{smDeviceId} AND create_time between #{startTime} and #{endTime} GROUP BY DATE_FORMAT(create_time, '%Y-%m-%d %H:00') ORDER BY DATE_FORMAT(create_time, '%Y-%m-%d %H:00')")
    public List<Map<String, Object>> selectTempHistory(@Param("smDeviceId") Long smDeviceId,
            @Param("startTime") String startTime, @Param("endTime") String endTime);

    // xinlv: 心率
    @Select("SELECT DATE_FORMAT(create_time, '%Y-%m-%d %H:00') AS time,AVG(xinlv) AS data FROM manage_sm_data WHERE sm_device_id = #{smDeviceId} AND create_time between #{startTime} and #{endTime} GROUP BY DATE_FORMAT(create_time, '%Y-%m-%d %H:00') ORDER BY DATE_FORMAT(create_time, '%Y-%m-%d %H:00')")
    public List<Map<String, Object>> selectXinlvHistory(@Param("smDeviceId") Long smDeviceId,
            @Param("startTime") String startTime, @Param("endTime") String endTime);

    // xueyang: 血氧
    @Select("SELECT DATE_FORMAT(create_time, '%Y-%m-%d %H:00') AS time,AVG(xue_yang) AS data FROM manage_sm_data WHERE sm_device_id = #{smDeviceId} AND create_time between #{startTime} and #{endTime} GROUP BY DATE_FORMAT(create_time, '%Y-%m-%d %H:00') ORDER BY DATE_FORMAT(create_time, '%Y-%m-%d %H:00')")
    public List<Map<String, Object>> selectXueYangHistory(@Param("smDeviceId") Long smDeviceId,
            @Param("startTime") String startTime, @Param("endTime") String endTime);

    /**
     * 
     * SELECT
     * DATE_FORMAT(m.minute, '%Y-%m-%d %H:%i') AS minute,
     * CASE WHEN COUNT(d.sm_device_id ) > 0 THEN 1 ELSE 0 END AS is_online
     * FROM (
     * SELECT
     * TIMESTAMP('2025-01-01 00:00:00') + INTERVAL (a.a + b.a*10 + c.a*100 +
     * d.a*1000) MINUTE AS minute
     * FROM
     * (SELECT 0 a UNION SELECT 1 UNION SELECT 2 UNION SELECT 3 UNION SELECT 4 UNION
     * SELECT 5 UNION SELECT 6 UNION SELECT 7 UNION SELECT 8 UNION SELECT 9) a,
     * (SELECT 0 a UNION SELECT 1 UNION SELECT 2 UNION SELECT 3 UNION SELECT 4 UNION
     * SELECT 5 UNION SELECT 6 UNION SELECT 7 UNION SELECT 8 UNION SELECT 9) b,
     * (SELECT 0 a UNION SELECT 1 UNION SELECT 2 UNION SELECT 3 UNION SELECT 4 UNION
     * SELECT 5 UNION SELECT 6 UNION SELECT 7 UNION SELECT 8 UNION SELECT 9) c,
     * (SELECT 0 a UNION SELECT 1) d
     * ) m
     * LEFT JOIN manage_sm_data d
     * ON d.sm_device_id = 1276
     * AND d.create_time >= m.minute
     * AND d.create_time < m.minute + INTERVAL 1 MINUTE
     * WHERE m.minute BETWEEN '2025-01-01 00:00:00' AND '2025-01-01 23:59:00'
     * GROUP BY m.minute
     * ORDER BY m.minute;
     * 
     * 
     */

    @Select("SELECT DATE_FORMAT(m.minute, '%Y-%m-%d %H:%i') AS minute," +
            "CASE WHEN COUNT(d.sm_device_id) > 0 THEN 1 ELSE 0 END AS is_online " +
            "FROM ( " +
            "  SELECT TIMESTAMP(#{startTime}) + INTERVAL (a.a + b.a*10 + c.a*100 + d.a*1000) MINUTE AS minute " +
            "  FROM (SELECT 0 a UNION SELECT 1 UNION SELECT 2 UNION SELECT 3 UNION SELECT 4 " +
            "        UNION SELECT 5 UNION SELECT 6 UNION SELECT 7 UNION SELECT 8 UNION SELECT 9) a, " +
            "       (SELECT 0 a UNION SELECT 1 UNION SELECT 2 UNION SELECT 3 UNION SELECT 4 " +
            "        UNION SELECT 5 UNION SELECT 6 UNION SELECT 7 UNION SELECT 8 UNION SELECT 9) b, " +
            "       (SELECT 0 a UNION SELECT 1 UNION SELECT 2 UNION SELECT 3 UNION SELECT 4 " +
            "        UNION SELECT 5 UNION SELECT 6 UNION SELECT 7 UNION SELECT 8 UNION SELECT 9) c, " +
            "       (SELECT 0 a UNION SELECT 1) d " +
            ") m " +
            "LEFT JOIN manage_sm_data d ON d.sm_device_id = #{smDeviceId} " +
            " AND d.create_time >= m.minute " +
            " AND d.create_time < m.minute + INTERVAL 1 MINUTE " +
            "WHERE m.minute BETWEEN #{startTime} AND #{endTime} " +
            "GROUP BY m.minute " +
            "ORDER BY m.minute")
    public List<Map<String, Object>> selectIsOnlineHistory(@Param("smDeviceId") Long smDeviceId,
            @Param("startTime") String startTime, @Param("endTime") String endTime);

}
