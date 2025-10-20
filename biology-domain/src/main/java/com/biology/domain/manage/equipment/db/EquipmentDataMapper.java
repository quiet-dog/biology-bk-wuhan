package com.biology.domain.manage.equipment.db;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.biology.domain.manage.equipment.dto.EquipmentDataStockDTO;
import com.biology.domain.manage.equipment.dto.TotalTimeDTO;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface EquipmentDataMapper extends BaseMapper<EquipmentDataEntity> {

        @Select("select AVG(equipment_data) AS data,CONCAT(LPAD(FLOOR(HOUR(create_time) / 1) * 1, 2, '0'), ':', LPAD(FLOOR(MINUTE(create_time) / 30) * 30, 2, '0')) AS time from manage_equipment_data_${suffix}"
                        + " WHERE create_time >= CURDATE() AND threshold_id = #{thresholdId}"
                        + " GROUP BY time"
                        + " ORDER BY time")
        public List<EquipmentDataStockDTO> getEquipmentDataStockDay(@Param("suffix") String suffix,
                        @Param("thresholdId") Long thresholdId);

        @Select("SELECT SUM(CASE WHEN create_time < 60 THEN create_time ELSE 0 END) AS totalTime"
                        + " FROM (SELECT equipment_id,TIMESTAMPDIFF(MINUTE, LAG(create_time) OVER (PARTITION BY equipment_id ORDER BY create_time), create_time) AS create_time"
                        + " FROM manage_equipment_data WHERE monitoring_indicator = '电压' AND equipment_id = #{equipmentId}"
                        + " ) as subquery")
        public Integer getTotalTime(@Param("equipmentId") Long equipmentId);

        // 我需要00:00-23:00的所有时间, 从00:00开始,每个小时的平均值,时间返回00:00-23:00,如果时间没数据的话返回时间数据为null
        /**
         * WITH RECURSIVE hours AS (
         * SELECT CAST('2025-09-18 00:00:00' AS DATETIME) AS hour_point
         * UNION ALL
         * SELECT DATE_ADD(hour_point, INTERVAL 1 HOUR)
         * FROM hours
         * WHERE hour_point < '2025-09-18 23:00:00'
         * )
         * SELECT
         * DATE_FORMAT(h.hour_point, '%H:00') AS time,
         * AVG(m.equipment_data) AS data
         * FROM hours h
         * LEFT JOIN manage_equipment_data m
         * ON m.create_time >= h.hour_point
         * AND m.create_time < DATE_ADD(h.hour_point, INTERVAL 1 HOUR)
         * GROUP BY h.hour_point
         * ORDER BY h.hour_point;
         * 
         * @param thresholdId
         * @param currenTime
         * @return
         */

        // 将上面的sql转换为mybatis的sql,并且将时间改为currenTime,再加上thresholdId和currenTime
        @Select({
                        "WITH RECURSIVE hours AS (",
                        "    SELECT CAST(CONCAT(#{currenTime}, ' 00:00:00') AS DATETIME) AS hour_point",
                        "    UNION ALL",
                        "    SELECT DATE_ADD(hour_point, INTERVAL 1 HOUR)",
                        "    FROM hours",
                        "    WHERE hour_point < CAST(CONCAT(#{currenTime}, ' 23:00:00') AS DATETIME)",
                        ")",
                        "SELECT",
                        "    DATE_FORMAT(h.hour_point, '%H:00') AS time,",
                        "    AVG(m.equipment_data) AS data",
                        "FROM hours h",
                        "LEFT JOIN manage_equipment_data m",
                        "       ON m.create_time >= h.hour_point",
                        "      AND m.create_time < DATE_ADD(h.hour_point, INTERVAL 1 HOUR)",
                        "      AND m.threshold_id = #{thresholdId}",
                        "GROUP BY h.hour_point",
                        "ORDER BY h.hour_point"
        })
        public List<EquipmentDataStockDTO> getHistoryCurentDay(@Param("thresholdId") Long thresholdId,
                        @Param("currenTime") String currenTime);

}