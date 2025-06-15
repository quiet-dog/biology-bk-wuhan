package com.biology.domain.manage.detection.db;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.biology.domain.manage.detection.dto.DetectionAreaTypeDTO;
import com.biology.domain.manage.detection.dto.DetectionStatisticsDTO;
import com.biology.domain.manage.detection.dto.PowerDTO;

public interface DetectionMapper extends BaseMapper<DetectionEntity> {
        @Select("SELECT DATE_FORMAT(create_time, '%H:%i:%s') AS data_time,value FROM manage_environment_detection WHERE environment_id = #{environmentId}"
                        + " AND DATE(create_time) = CURDATE()"
                        + " GROUP BY DATE_FORMAT(create_time, '%H:%i:%s'),value"
                        + " ORDER BY data_time")
        public List<DetectionStatisticsDTO> getStatistics(@Param("environmentId") Long environmentId);

        @Select("SELECT CONCAT(LPAD(HOUR(d.create_time), 2, '0'), ':00') AS time,AVG(d.power) AS data"
                        + " FROM manage_environment_detection AS d"
                        + " JOIN manage_environment AS e ON e.environment_id = d.environment_id"
                        + " WHERE d.create_time >= CURDATE()"
                        + " AND e.type = #{des}"
                        + " GROUP BY CONCAT(LPAD(HOUR(d.create_time), 2, '0'), ':00')"
                        + " ORDER BY CONCAT(LPAD(HOUR(d.create_time), 2, '0'), ':00')")
        public List<PowerDTO> getPowerStatic(@Param("des") String des);

        // 获取一周内的数据
        @Select("SELECT DATE_FORMAT(d.create_time, '%Y-%m-%d') AS time,AVG(d.power) AS data"
                        + " FROM manage_environment_detection AS d"
                        + " JOIN manage_environment AS e ON e.environment_id = d.environment_id"
                        + " WHERE d.create_time >= CURDATE() - INTERVAL 6 DAY AND d.create_time < CURDATE() + INTERVAL 1 DAY"
                        + " AND e.description = #{des}"
                        + " GROUP BY DATE_FORMAT(d.create_time, '%Y-%m-%d')"
                        + " ORDER BY DATE_FORMAT(d.create_time, '%Y-%m-%d')")
        public List<PowerDTO> getPowerStaticWeek(@Param("des") String des);

        // 获取一个月内的数据
        @Select("SELECT DATE_FORMAT(d.create_time, '%Y-%m-%d') AS time,AVG(d.power) AS data"
                        + " FROM manage_environment_detection AS d"
                        + " JOIN manage_environment AS e ON e.environment_id = d.environment_id"
                        + " WHERE d.create_time >= CURDATE() - INTERVAL 1 MONTH AND d.create_time <= NOWZ()"
                        + " AND e.description = #{des}"
                        + " GROUP BY DATE_FORMAT(d.create_time, '%Y-%m-%d')"
                        + " ORDER BY DATE_FORMAT(d.create_time, '%Y-%m-%d')")
        public List<PowerDTO> getPowerStaticMonth(@Param("des") String des);

        // 获取指定时间范围内的数据 getPowerStaticByDateRange
        @Select("SELECT DATE_FORMAT(d.create_time, '%Y-%m-%d') AS time,AVG(d.power) AS data"
                        + " FROM manage_environment_detection AS d"
                        + " JOIN manage_environment AS e ON e.environment_id = d.environment_id"
                        + " WHERE d.create_time >= #{startTime} AND d.create_time <= #{endTime}"
                        + " AND e.description = #{des}"
                        + " GROUP BY DATE_FORMAT(d.create_time, '%Y-%m-%d')"
                        + " ORDER BY DATE_FORMAT(d.create_time, '%Y-%m-%d')")
        public List<PowerDTO> getPowerStaticByDateRange(@Param("des") String des,
                        @Param("startTime") java.time.LocalDateTime startTime,
                        @Param("endTime") java.time.LocalDateTime endTime);

        // 获取一年内每个月的数据
        @Select("SELECT DATE_FORMAT(d.create_time, '%Y-%m') AS time,AVG(d.power) AS data"
                        + " FROM manage_environment_detection AS d"
                        + " JOIN manage_environment AS e ON e.environment_id = d.environment_id"
                        + " WHERE d.create_time >= DATE_SUB(CURDATE(), INTERVAL 11 MONTH)  AND d.create_time < LAST_DAY(CURDATE()) + INTERVAL 1 DAY"
                        + " AND e.description = #{des}"
                        + " GROUP BY DATE_FORMAT(d.create_time, '%Y-%m')"
                        + " ORDER BY DATE_FORMAT(d.create_time, '%Y-%m')")
        public List<PowerDTO> getPowerStaticYear(@Param("des") String des);

        // 获取当天的每个时间点的数据1点2点3点...
        @Select("SELECT DATE_FORMAT(d.create_time, '%H:%i:%s') AS time,AVG(d.value) as data FROM manage_environment_detection as d"
                        + " JOIN manage_environment as e ON e.environment_id = d.environment_id"
                        + " WHERE e.description = #{abc} AND e.unit_name = #{unitName}"
                        + " AND DATE(d.create_time) = CURDATE()"
                        + " GROUP BY DATE_FORMAT(d.create_time, '%H:%i:%s')"
                        + " ORDER BY DATE_FORMAT(d.create_time, '%H:%i:%s')")
        public List<PowerDTO> getHistoryDataFormat(@Param("abc") String abc, @Param("unitName") String unitName);

        @Select("SELECT e.e_area,AVG(d.value) as data from manage_environment_detection as d JOIN manage_environment as e ON e.environment_id = d.environment_id"
                        + " WHERE e.unit_name = #{unitName}"
                        + " AND DATE(d.create_time) = CURDATE()"
                        + " GROUP BY e.e_area")
        public List<DetectionAreaTypeDTO> getDetectionCountTypeArea(@Param("unitName") String unitName);

        @Select("SELECT e.e_area,AVG(d.value) as data from manage_environment_detection as d JOIN manage_environment as e ON e.environment_id = d.environment_id"
                        + " WHERE e.unit_name = #{unitName} AND e.description = #{description}"
                        + " AND DATE(d.create_time) = CURDATE()"
                        + " GROUP BY e.e_area")
        public List<DetectionAreaTypeDTO> getDetectionCountTypeAreaAndName(@Param("unitName") String unitName,
                        @Param("name") String description);

        @Select("SELECT CONCAT(LPAD(HOUR(create_time), 2, '0'), ':00') AS time,SUM(power) as data FROM manage_environment_detection"
                        + " WHERE create_time >= CURDATE())"
                        + " GROUP BY CONCAT(LPAD(HOUR(create_time), 2, '0'), ':00')"
                        + " ORDER BY CONCAT(LPAD(HOUR(create_time), 2, '0'), ':00')")
        public List<PowerDTO> getHistoryPowersByType();

        @Select("SELECT CONCAT(LPAD(HOUR(create_time), 2, '0'), ':00') AS time,SUM(water_value) as data FROM manage_environment_detection"
                        + " WHERE create_time >= CURDATE()"
                        + " GROUP BY CONCAT(LPAD(HOUR(create_time), 2, '0'), ':00')"
                        + " ORDER BY CONCAT(LPAD(HOUR(create_time), 2, '0'), ':00')")
        public List<PowerDTO> getHistoryWaterPowersByType();

        @Select("SELECT DATE_FORMAT(create_time, '%Y-%m-%d') AS time,SUM(power) as data FROM manage_environment_detection"
                        + " WHERE create_time >= CURDATE() - INTERVAL 6 DAY AND create_time < CURDATE() + INTERVAL 1 DAY"
                        + " GROUP BY DATE_FORMAT(create_time, '%Y-%m-%d')"
                        + " ORDER BY DATE_FORMAT(create_time, '%Y-%m-%d')")
        public List<PowerDTO> getHistoryPowersByTypeWeek();

        @Select("SELECT DATE_FORMAT(create_time, '%Y-%m-%d') AS time,SUM(power) as data FROM manage_environment_detection"
                        + " WHERE create_time >= CURDATE() - INTERVAL 1 MONTH AND create_time <= NOW()"
                        + " GROUP BY DATE_FORMAT(create_time, '%Y-%m-%d')"
                        + " ORDER BY DATE_FORMAT(create_time, '%Y-%m-%d')")
        public List<PowerDTO> getHistoryPowersByTypeMonth();

        @Select("SELECT DATE_FORMAT(create_time, '%m') AS time,SUM(power) as data FROM manage_environment_detection"
                        + " WHERE YEAR(create_time) = YEAR(CURDATE())"
                        + " GROUP BY MONTH(create_time)"
                        + " ORDER BY MONTH(create_time)")
        public List<PowerDTO> getHistoryPowersByTypeYear();

        @Select("SELECT CONCAT(LPAD(HOUR(create_time), 2, '0'), ':00') AS time,SUM(water_value) as data FROM manage_environment_detection"
                        + " WHERE create_time >= CURDATE()"
                        + " GROUP BY CONCAT(LPAD(HOUR(create_time), 2, '0'), ':00')"
                        + " ORDER BY CONCAT(LPAD(HOUR(create_time), 2, '0'), ':00')")
        public List<PowerDTO> getHistoryWaterByType();

        @Select("SELECT DATE_FORMAT(create_time, '%Y-%m-%d') AS time,SUM(water_value) as data FROM manage_environment_detection"
                        + " WHERE create_time >= CURDATE() - INTERVAL 6 DAY AND create_time < CURDATE() + INTERVAL 1 DAY"
                        + " GROUP BY DATE_FORMAT(create_time, '%Y-%m-%d')"
                        + " ORDER BY DATE_FORMAT(create_time, '%Y-%m-%d')")
        public List<PowerDTO> getHistoryWaterByTypeWeek();

        @Select("SELECT DATE_FORMAT(create_time, '%Y-%m-%d') AS time,SUM(water_value) as data FROM manage_environment_detection"
                        + " WHERE create_time >= CURDATE() - INTERVAL 1 MONTH AND create_time <= NOW()"
                        + " GROUP BY DATE_FORMAT(create_time, '%Y-%m-%d')"
                        + " ORDER BY DATE_FORMAT(create_time, '%Y-%m-%d')")
        public List<PowerDTO> getHistoryWaterByTypeMonth();

        @Select("SELECT DATE_FORMAT(create_time, '%Y-%m') AS time,SUM(water_value) as data FROM manage_environment_detection"
                        + " WHERE create_time >= DATE_SUB(CURDATE(), INTERVAL 1 YEAR)"
                        + " GROUP BY DATE_FORMAT(create_time, '%Y-%m')"
                        + " ORDER BY DATE_FORMAT(create_time, '%Y-%m')")
        public List<PowerDTO> getHistoryWaterByTypeYear();

        @Select("SELECT CONCAT(LPAD(HOUR(create_time), 2, '0'), ':00') AS time,SUM(electricity_value) as data FROM manage_environment_detection"
                        + " WHERE create_time >= CURDATE() "
                        + " GROUP BY CONCAT(LPAD(HOUR(create_time), 2, '0'), ':00')"
                        + " ORDER BY CONCAT(LPAD(HOUR(create_time), 2, '0'), ':00')")
        public List<PowerDTO> getHistoryElectricityByType();

        @Select("SELECT DATE_FORMAT(create_time, '%Y-%m-%d') AS time,SUM(electricity_value) as data FROM manage_environment_detection"
                        + " WHERE create_time >= CURDATE() - INTERVAL 6 DAY AND create_time < CURDATE() + INTERVAL 1 DAY"
                        + " GROUP BY DATE_FORMAT(create_time, '%Y-%m-%d')"
                        + " ORDER BY DATE_FORMAT(create_time, '%Y-%m-%d')")
        public List<PowerDTO> getHistoryElectricityByTypeWeek();

        @Select("SELECT DATE_FORMAT(create_time, '%Y-%m-%d') AS time,SUM(electricity_value) as data FROM manage_environment_detection"
                        + " WHERE create_time >= CURDATE() - INTERVAL 1 MONTH AND create_time <= NOW()"
                        + " GROUP BY DATE_FORMAT(create_time, '%Y-%m-%d')"
                        + " ORDER BY DATE_FORMAT(create_time, '%Y-%m-%d')")
        public List<PowerDTO> getHistoryElectricityByTypeMonth();

        @Select("SELECT DATE_FORMAT(create_time, '%Y-%m') AS time,SUM(electricity_value) as data FROM manage_environment_detection"
                        + " WHERE create_time >= DATE_SUB(CURDATE(), INTERVAL 1 YEAR)"
                        + " GROUP BY DATE_FORMAT(create_time, '%Y-%m')"
                        + " ORDER BY DATE_FORMAT(create_time, '%Y-%m')")
        public List<PowerDTO> getHistoryElectricityByTypeYear();

        @Select("SELECT CONCAT(LPAD(HOUR(d.create_time), 2, '0'), ':00') AS time,SUM(water_value) as data FROM manage_environment_detection as d"
                        + " JOIN manage_environment as e ON e.environment_id = d.environment_id"
                        + " WHERE d.create_time >= CURDATE()"
                        + " GROUP BY CONCAT(LPAD(HOUR(d.create_time), 2, '0'), ':00'),e.e_area")
        public List<PowerDTO> getHistoryElectricityByArea();

        @Select("SELECT e.e_area AS time,SUM(d.water_value) as data FROM manage_environment_detection as d"
                        + " JOIN manage_environment as e ON e.environment_id = d.environment_id"
                        + " WHERE d.create_time >= CURDATE() - INTERVAL 6 DAY AND d.create_time < CURDATE() + INTERVAL 1 DAY"
                        + " GROUP BY e.e_area")
        public List<PowerDTO> getHistoryWaterByAreaWeek();

        @Select("SELECT e.e_area AS time,SUM(d.electricity_value) as data FROM manage_environment_detection as d"
                        + " JOIN manage_environment as e ON e.environment_id = d.environment_id"
                        + " WHERE d.create_time >= CURDATE() - INTERVAL 6 DAY AND d.create_time < CURDATE() + INTERVAL 1 DAY"
                        + " GROUP BY e.e_area")
        public List<PowerDTO> getHistoryElectricityByAreaWeek();

        @Select("SELECT e.e_area AS time,SUM(d.water_value) as data FROM manage_environment_detection as d"
                        + " JOIN manage_environment as e ON e.environment_id = d.environment_id"
                        + " WHERE d.create_time >= CURDATE() - INTERVAL 1 MONTH AND d.create_time <= NOW()"
                        + " GROUP BY e.e_area")
        public List<PowerDTO> getHistoryWaterByAreaMonth();

        @Select("SELECT e.e_area AS time,SUM(d.electricity_value) as data FROM manage_environment_detection as d"
                        + " JOIN manage_environment as e ON e.environment_id = d.environment_id"
                        + " WHERE d.create_time >= CURDATE() - INTERVAL 1 MONTH AND d.create_time <= NOW()"
                        + " GROUP BY e.e_area")
        public List<PowerDTO> getHistoryElectricityByAreaMonth();

        @Select("SELECT e.e_area AS time,SUM(d.water_value) as data FROM manage_environment_detection as d"
                        + " JOIN manage_environment as e ON e.environment_id = d.environment_id"
                        + " WHERE d.create_time >= DATE_SUB(CURDATE(), INTERVAL 1 YEAR)"
                        + " GROUP BY e.e_area")
        public List<PowerDTO> getHistoryWaterByAreaYear();

        @Select("SELECT e.e_area AS time,SUM(d.electricity_value) as data FROM manage_environment_detection as d"
                        + " JOIN manage_environment as e ON e.environment_id = d.environment_id"
                        + " WHERE d.create_time >= DATE_SUB(CURDATE(), INTERVAL 1 YEAR)"
                        + " GROUP BY e.e_area")
        public List<PowerDTO> getHistoryElectricityByAreaYear();

        @Select("SELECT DATE_FORMAT(d.create_time, '%Y-%m-%d') AS time,AVG(d.value) as data FROM manage_environment_detection as d"
                        + " JOIN manage_environment as e ON e.environment_id = d.environment_id"
                        + " WHERE d.create_time >= CURDATE() - INTERVAL 6 DAY AND d.create_time < CURDATE() + INTERVAL 1 DAY"
                        + " AND e.unit_name = #{unitName}"
                        + " AND e.description = #{area}"
                        + " GROUP BY DATE_FORMAT(d.create_time, '%Y-%m-%d')"
                        + " ORDER BY DATE_FORMAT(d.create_time, '%Y-%m-%d')")
        public List<PowerDTO> getAareUnitNameHistoryWeek(@Param("unitName") String unitName,
                        @Param("area") String area);

        @Select("SELECT DATE_FORMAT(d.create_time, '%Y-%m-%d') AS time,AVG(d.value) as data FROM manage_environment_detection as d"
                        + " JOIN manage_environment as e ON e.environment_id = d.environment_id"
                        + " WHERE d.create_time >= CURDATE() - INTERVAL 1 MONTH AND d.create_time <= NOW()"
                        + " AND e.unit_name = #{unitName}"
                        + " AND e.description = #{area}"
                        + " GROUP BY DATE_FORMAT(d.create_time, '%Y-%m-%d')"
                        + " ORDER BY DATE_FORMAT(create_time, '%Y-%m-%d')")
        public List<PowerDTO> getAareUnitNameHistoryMonth(@Param("unitName") String unitName,
                        @Param("area") String area);

        @Select("SELECT DATE_FORMAT(d.create_time, '%m') AS time,AVG(d.value) as data FROM manage_environment_detection as d"
                        + " JOIN manage_environment as e ON e.environment_id = d.environment_id"
                        + " WHERE YEAR(d.create_time) = YEAR(CURDATE())"
                        + " AND e.unit_name = #{unitName}"
                        + " AND e.description = #{area}"
                        + " GROUP BY DATE_FORMAT(d.create_time, '%m')"
                        + " ORDER BY DATE_FORMAT(d.create_time, '%m')")
        public List<PowerDTO> getAareUnitNameHistoryYear(@Param("unitName") String unitName,
                        @Param("area") String area);

        @Select("SELECT CONCAT(LPAD(HOUR(create_time), 2, '0'), ':00') as time,AVG(value) as data FROM manage_environment_detection"
                        + " WHERE environment_id = #{environmentId}"
                        + " AND create_time >= CURDATE()"
                        + " GROUP BY CONCAT(LPAD(HOUR(create_time), 2, '0'), ':00') "
                        + " ORDER BY CONCAT(LPAD(HOUR(create_time), 2, '0'), ':00') ")
        public List<PowerDTO> getHistoryDayByEnvironmentId(@Param("environmentId") Long environmentId);

        @Select("SELECT description AS time, value AS data"
                        + " FROM (SELECT d.value,e.description,ROW_NUMBER() OVER (PARTITION BY e.environment_id ORDER BY d.create_time DESC) AS rn"
                        + " FROM manage_environment_detection AS d"
                        + " LEFT JOIN manage_environment AS e ON e.environment_id = d.environment_id"
                        + " WHERE e.e_area = #{area} AND e.unit_name = #{unitName}"
                        + " ) AS RankedData"
                        + "  WHERE rn = 1 AND value IS NOT NULL")
        public List<PowerDTO> getZuiXinShuJu(@Param("area") String area,
                        @Param("unitName") String unitName);

        // 获取本月的电量使用量 electricity_value
        @Select("SELECT SUM(electricity_value) FROM manage_environment_detection"
                        + " WHERE environment_id = #{environmentId} AND MONTH(create_time) = MONTH(CURDATE())"
                        + " AND YEAR(create_time) = YEAR(CURDATE())"
                        + " AND electricity_value is not null")
        public Double getCurrentMonthPowerUsage(@Param("environmentId") Long environmentId);

        // 获取水量使用量 water_value
        @Select("SELECT SUM(water_value) FROM manage_environment_detection"
                        + " WHERE environment_id = #{environmentId} AND MONTH(create_time) = MONTH(CURDATE())"
                        + " AND YEAR(create_time) = YEAR(CURDATE())"
                        + " AND water_value is not null")
        public Double getCurrentMonthWaterUsage(@Param("environmentId") Long environmentId);
}
