package com.biology.domain.manage.event.db;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.biology.domain.manage.detection.dto.DareaDTO;
import com.biology.domain.manage.event.dto.AllEventEchartDTO;
import com.biology.domain.manage.event.dto.AreaStatisticsDTO;
import com.biology.domain.manage.event.dto.EnvironmentStock;
import com.biology.domain.manage.event.dto.HistoryEventAll;
import com.biology.domain.manage.event.dto.WeekStatisticsDTO;

public interface EventMapper extends BaseMapper<EventEntity> {
        @Select("select type,DATE(create_time) AS date, COUNT(*) AS count from manage_event"
                        + " WHERE create_time >= CURDATE() - INTERVAL 6 DAY"
                        + " GROUP BY type,DATE_FORMAT(create_time, '%m-%d')"
                        + " ORDER BY type")
        public List<WeekStatisticsDTO> getWeekStatistics();

        @Select("select type,DATE_FORMAT(create_time, '%Y-%m') AS date, COUNT(*) AS count from manage_event"
                        + " WHERE DATE_FORMAT(create_time, '%Y-%m') = DATE_FORMAT(NOW(), '%Y-%m')"
                        + " GROUP BY type,DATE_FORMAT(create_time, '%Y-%m')"
                        + " ORDER BY type")
        public List<WeekStatisticsDTO> getMonthStatistics();

        @Select("select type,DATE_FORMAT(create_time, '%Y') AS date, COUNT(*) AS count from manage_event"
                        + " WHERE DATE_FORMAT(create_time, '%Y') = DATE_FORMAT(NOW(), '%Y')"
                        + " GROUP BY type,DATE_FORMAT(create_time, '%Y')"
                        + " ORDER BY type")
        public List<WeekStatisticsDTO> getYearStatistics();

        @Select("SELECT type,DATE_FORMAT(create_time,'%Y-%m-%d')  as data_time , COUNT(*) AS count,"
                        + " FORMAT((COUNT(DISTINCT equipment_id) / (SELECT COUNT(*) FROM manage_equipment)),2) as rate"
                        + " from manage_event"
                        + " WHERE create_time >= CURDATE() - INTERVAL 1 MONTH AND create_time <= NOW()"
                        + " AND YEAR(create_time) = YEAR(CURDATE())"
                        + " GROUP BY type,DATE_FORMAT(create_time, '%Y-%m-%d')"
                        + " ORDER BY type")
        public List<WeekStatisticsDTO> getNowMonthStatistics();

        @Select("SELECT type,DATE_FORMAT(create_time,'%Y-%m-%d')  as data_time , COUNT(*) AS count from manage_event"
                        + " WHERE create_time >= CURDATE() - INTERVAL 1 MONTH AND create_time <= NOW()"
                        + " AND YEAR(create_time) = YEAR(CURDATE())"
                        + " AND type = #{eventType}"
                        + " GROUP BY type,DATE_FORMAT(create_time, '%Y-%m-%d')"
                        + " ORDER BY type")
        public List<WeekStatisticsDTO> getNowMonthStatisticsDeviceName(String eventType);

        @Select("SELECT type,DATE_FORMAT(create_time,'%Y-%m')  as data_time , COUNT(*) AS count,"
                        + " FORMAT((COUNT(DISTINCT equipment_id) / (SELECT COUNT(*) FROM manage_equipment)),2) as rate"
                        + " from manage_event"
                        + " WHERE create_time >= DATE_SUB(CURDATE(), INTERVAL 11 MONTH)"
                        + "  AND create_time < LAST_DAY(CURDATE()) + INTERVAL 1 DAY"
                        + " GROUP BY type,DATE_FORMAT(create_time, '%Y-%m')"
                        + " ORDER BY type,DATE_FORMAT(create_time, '%Y-%m')")
        public List<WeekStatisticsDTO> getNowYearStatistics();

        @Select("SELECT type,DATE_FORMAT(create_time,'%Y-%m')  as data_time , COUNT(*) AS count from manage_event"
                        + " WHERE create_time >= DATE_SUB(CURDATE(), INTERVAL 11 MONTH)"
                        + " AND create_time < LAST_DAY(CURDATE()) + INTERVAL 1 DAY"
                        + " AND type = #{eventType}"
                        + " GROUP BY type,DATE_FORMAT(create_time, '%Y-%m')"
                        + " ORDER BY type,DATE_FORMAT(create_time, '%Y-%m')")
        public List<WeekStatisticsDTO> getNowYearStatisticsDeviceName(String eventType);

        @Select("SELECT type,DATE_FORMAT(create_time,'%m-%d')  as data_time , COUNT(*) AS count,"
                        + " FORMAT((COUNT(DISTINCT equipment_id) / (SELECT COUNT(*) FROM manage_equipment)),2) as rate"
                        + " from manage_event"
                        + " WHERE create_time >= CURDATE() - INTERVAL 6 DAY AND deleted = 0"
                        + " GROUP BY type,DATE_FORMAT(create_time, '%m-%d')"
                        + " ORDER BY type")
        public List<WeekStatisticsDTO> getNowWeekStatistics();

        @Select("SELECT type,DATE_FORMAT(create_time,'%m-%d')  as data_time , COUNT(*) AS count from manage_event"
                        + " WHERE create_time >= CURDATE() - INTERVAL 6 DAY"
                        + " AND type = #{eventType}"
                        + " GROUP BY type,DATE_FORMAT(create_time, '%m-%d')"
                        + " ORDER BY type")
        public List<WeekStatisticsDTO> getNowWeekStatisticsDeviceName(String eventType);

        @Select("select type,DATE_FORMAT(create_time, '%Y-%m-%d') AS date, COUNT(*) AS count from manage_event"
                        + " GROUP BY type,DATE_FORMAT(create_time, '%Y-%m-%d')"
                        + " ORDER BY type")
        public List<WeekStatisticsDTO> getEveryWeekStatistics();

        @Select("select type,DATE_FORMAT(create_time, '%Y-%m') AS date, COUNT(*) AS count from manage_event"
                        + " GROUP BY type,DATE_FORMAT(create_time, '%Y-%m')"
                        + " ORDER BY type")
        public List<WeekStatisticsDTO> getEveryMonthStatistics();

        @Select("select type,DATE_FORMAT(create_time, '%Y-%m') AS date, COUNT(*) AS count from manage_event"
                        + " GROUP BY type,DATE_FORMAT(create_time, '%Y')"
                        + " ORDER BY type")
        public List<WeekStatisticsDTO> getEveryYearStatistics();

        // 获取区域统计
        // @Select("SELECT q.installation_location as manufacturer, COUNT(*) AS count
        // from manage_event as e"
        // + " JOIN manage_equipment q on e.equipment_id = q.equipment_id"
        // + " WHERE e.create_time BETWEEN #{startTime} AND #{endTime}"
        // + " GROUP BY q.installation_location")
        @Select("SELECT m.e_area as manufacturer, COUNT(*) AS count from manage_emergency_event as e"
                        + " LEFT JOIN manage_emergency_event_alarm n on e.emergency_event_id = n.emergency_event_id"
                        + " LEFT JOIN manage_emergency_alarm a on a.emergency_alarm_id = n.emergency_alarm_id"
                        + " LEFT JOIN manage_environment_detection d on d.detection_id = a.detection_id"
                        + " LEFT JOIN manage_environment m on m.environment_id = d.environment_id"
                        + " WHERE e.create_time BETWEEN #{startTime} AND CONCAT(#{endTime}, ' 23:59:59')"
                        + " AND e.type = '环境报警类'"
                        + " GROUP BY m.e_area")
        public List<AreaStatisticsDTO> getAreaStatistics(@Param("startTime") String startTime,
                        @Param("endTime") String endTime);

        // m.e_area
        @Select("SELECT e.type as area, COUNT(*) AS avgValue,DATE_FORMAT(e.create_time, '%Y-%m-%d') AS timeSlot from manage_emergency_event as e"
                        + " LEFT JOIN manage_emergency_event_alarm n on e.emergency_event_id = n.emergency_event_id"
                        + " LEFT JOIN manage_emergency_alarm a on a.emergency_alarm_id = n.emergency_alarm_id"
                        + " LEFT JOIN manage_environment_detection d on d.detection_id = a.detection_id"
                        + " LEFT JOIN manage_environment m on m.environment_id = d.environment_id"
                        + " WHERE e.create_time BETWEEN #{startTime} AND CONCAT(#{endTime}, ' 23:59:59')"
                        // + " AND e.type = '环境报警类'"
                        + " GROUP BY e.type,DATE_FORMAT(e.create_time, '%Y-%m-%d')")
        public List<DareaDTO> getAreaStatisticsByDate(@Param("startTime") String startTime,
                        @Param("endTime") String endTime);

        // 获取今日报警数量
        @Select("SELECT COUNT(*) from manage_event"
                        + " WHERE DATE(create_time) = CURDATE()")
        public Integer getTodayAlarmCount();

        // 获取历史报警数量
        @Select("SELECT COUNT(*) from manage_event")
        public Integer getAlarmCount();

        @Select("SELECT DATE_FORMAT(create_time, '%m-%d') as time, COUNT(*) as count from manage_event"
                        + " WHERE create_time >= CURDATE() - INTERVAL 6 DAY"
                        + " GROUP BY DATE_FORMAT(create_time, '%m-%d')")
        public List<HistoryEventAll> getHistoryEventAllWeek();

        @Select("SELECT DATE_FORMAT(create_time, '%m-%d') as time, COUNT(*) as count from manage_event"
                        + " WHERE create_time >= CURDATE() - INTERVAL 1 MONTH"
                        + " GROUP BY DATE_FORMAT(create_time, '%m-%d')")
        public List<HistoryEventAll> getHistoryEventAllMonth();

        @Select("SELECT DATE_FORMAT(create_time, '%Y-%m') as time, COUNT(*) as count from manage_event"
                        + " WHERE create_time >= DATE_SUB(CURDATE(), INTERVAL 11 MONTH) AND create_time < LAST_DAY(CURDATE()) + INTERVAL 1 DAY"
                        + " GROUP BY DATE_FORMAT(create_time, '%Y-%m')")
        public List<HistoryEventAll> getHistoryEventAllYear();

        // @Select("SELECT n.unit_name,COUNT(*) as count FROM manage_event e JOIN
        // manage_environment n on n.environment_id = e.environment_id"
        // + " WHERE e.create_time >= CURDATE() - INTERVAL 6 DAY AND e.create_time <
        // CURDATE() + INTERVAL 1 DAY"
        // + " AND e.description = #{description}"
        // + " GROUP BY n.unit_name")
        // public List<EnvironmentStock>
        // getEnvrionmentEventAllWeek(@Param("description") String description);

        @Select("SELECT n.unit_name,COUNT(*) as count FROM manage_event e JOIN manage_environment n on n.environment_id = e.environment_id"
                        + " WHERE e.create_time >= CURDATE() - INTERVAL 6 DAY AND e.create_time < CURDATE() + INTERVAL 1 DAY"
                        + " AND e.deleted = 0 AND n.deleted = 0 AND n.unit_name != '电' AND n.unit_name != '水'"
                        + " GROUP BY n.unit_name")
        public List<EnvironmentStock> getEnvrionmentEventAllWeek();

        // @Select("SELECT n.unit_name,COUNT(*) as count FROM manage_event e JOIN
        // manage_environment n on n.environment_id = e.environment_id"
        // + " WHERE e.create_time >= CURDATE() - INTERVAL 1 MONTH AND e.create_time <=
        // CURDATE()"
        // + " AND e.description = #{description}"
        // + " GROUP BY n.unit_name")
        // public List<EnvironmentStock>
        // getEnvrionmentEventAllMonth(@Param("description") String description);

        @Select("SELECT n.unit_name,COUNT(*) as count FROM manage_event e JOIN manage_environment n on n.environment_id = e.environment_id"
                        + " WHERE e.create_time >= CURDATE() - INTERVAL 1 MONTH"
                        // + " WHERE e.create_time >= DATE_SUB(CURDATE(), INTERVAL 30 DAY)"
                        // +" WHERE YEARWEEK(DATE(e.create_time), 1) = YEARWEEK(CURDATE(), 1)"
                        + " AND n.unit_name != '电' AND n.unit_name != '水' AND n.deleted = 0"
                        + " GROUP BY n.unit_name")
        public List<EnvironmentStock> getEnvrionmentEventAllMonth();

        // @Select("SELECT n.unit_name,COUNT(*) as count FROM manage_event e JOIN
        // manage_environment n on n.environment_id = e.environment_id"
        // + " WHERE e.create_time >= CURDATE() - INTERVAL 1 YEAR AND e.create_time <=
        // CURDATE()"
        // + " AND e.description = #{description}"
        // + " GROUP BY n.unit_name")
        // public List<EnvironmentStock>
        // getEnvrionmentEventAllYear(@Param("description") String description);

        @Select("SELECT n.unit_name,COUNT(*) as count FROM manage_event e JOIN manage_environment n on n.environment_id = e.environment_id"
                        // + " WHERE e.create_time >= DATE_SUB(CURDATE(), INTERVAL 1 YEAR)"
                        + " WHERE e.create_time >= DATE_SUB(CURDATE(), INTERVAL 11 MONTH)"
                        + " AND e.create_time <= LAST_DAY(CURDATE())"
                        + " AND n.unit_name != '电' AND n.unit_name != '水' AND n.deleted = 0"
                        + " GROUP BY n.unit_name")
        public List<EnvironmentStock> getEnvrionmentEventAllYear();

        @Select("SELECT n.e_area,COUNT(*) as count FROM manage_event e JOIN manage_environment n on n.environment_id = e.environment_id"
                        + " WHERE e.create_time >= CURDATE() - INTERVAL 6 DAY AND e.create_time < CURDATE() + INTERVAL 1 DAY"
                        + " GROUP BY n.e_area")
        public List<EnvironmentStock> getAreaEnvrionmentEventAllWeek();

        @Select("SELECT n.e_area,COUNT(*) as count FROM manage_event e JOIN manage_environment n on n.environment_id = e.environment_id"
                        + " WHERE e.create_time >= CURDATE() - INTERVAL 1 MONTH"
                        // + " WHERE e.create_time >= DATE_SUB(CURDATE(), INTERVAL 30 DAY)"
                        + " GROUP BY n.e_area")

        public List<EnvironmentStock> getAreaEnvrionmentEventAllMonth();

        @Select("SELECT n.e_area,COUNT(*) as count FROM manage_event e JOIN manage_environment n on n.environment_id = e.environment_id"
                        // + " WHERE YEAR(e.create_time) = YEAR(CURDATE())"
                        + " WHERE e.create_time >= DATE_SUB(CURDATE(), INTERVAL 11 MONTH)"
                        + " AND e.create_time <= LAST_DAY(CURDATE())"
                        + " GROUP BY n.e_area")
        public List<EnvironmentStock> getAreaEnvrionmentEventAllYear();

        @Select("SELECT COUNT(*) as count,DATE_FORMAT(create_time, '%Y-%m-%d') as time from manage_event"
                        + " WHERE create_time >= CURDATE() - INTERVAL 6 DAY AND create_time < CURDATE() + INTERVAL 1 DAY"
                        + " AND environment_id = #{environmentId}"
                        + " GROUP BY DATE_FORMAT(create_time, '%Y-%m-%d')")
        public List<EnvironmentStock> getEnvironmentEventWeekById(Long environmentId);

        @Select("SELECT COUNT(*) as count,DATE_FORMAT(create_time, '%Y-%m-%d') as time from manage_event"
                        + " WHERE create_time >= CURDATE() - INTERVAL 1 MONTH"
                        // + " WHERE create_time >= DATE_SUB(CURDATE(), INTERVAL 30 DAY)"
                        + " AND environment_id = #{environmentId}"
                        + " GROUP BY DATE_FORMAT(create_time, '%Y-%m-%d')")

        public List<EnvironmentStock> getEnvironmentEventMonthById(Long environmentId);

        @Select("SELECT COUNT(*) as count,DATE_FORMAT(create_time, '%m') as time from manage_event"
                        // + " WHERE YEAR(create_time) = YEAR(CURDATE())"
                        + " WHERE create_time >= DATE_SUB(CURDATE(), INTERVAL 11 MONTH)"
                        + " AND create_time <= LAST_DAY(CURDATE())"
                        + " AND environment_id = #{environmentId}"
                        + " GROUP BY DATE_FORMAT(create_time, '%m')")
        public List<EnvironmentStock> getEnvironmentEventYearById(Long environmentId);

        // 获取区域统计
        /**
         * @Select("SELECT COALESCE(q.installation_location, n.e_area) AS
         * manufacturer,COUNT(*) as count FROM manage_event as e "
         * + " LEFT JOIN manage_equipment q on q.equipment_id = e.equipment_id AND
         * q.deleted = 0 "
         * + " LEFT JOIN manage_environment n on n.environment_id = e.environment_id AND
         * n.deleted = 0"
         * // + " WHERE e.create_time >= CURDATE() - INTERVAL 6 DAY AND e.create_time <
         * // CURDATE() + INTERVAL 1 DAY"
         * + " WHERE e.create_time >= DATE_ADD(CURDATE(), INTERVAL 1 -
         * DAYOFWEEK(CURDATE()) DAY)"
         * + " AND e.create_time < DATE_ADD(CURDATE(), INTERVAL 8 - DAYOFWEEK(CURDATE())
         * DAY)"
         * + " AND manufacturer IS NOT NULL"
         * + " GROUP BY COALESCE(q.installation_location, n.e_area)")
         * 
         * @return
         */
        @Select("SELECT manufacturer, COUNT(*) AS count " +
                        "FROM ( " +
                        "    SELECT COALESCE(q.installation_location, n.e_area) AS manufacturer " +
                        "    FROM manage_event AS e " +
                        "    LEFT JOIN manage_equipment q ON q.equipment_id = e.equipment_id " +
                        "    LEFT JOIN manage_environment n ON n.environment_id = e.environment_id " +
                        "    WHERE e.create_time >= DATE_ADD(CURDATE(), INTERVAL 1 - DAYOFWEEK(CURDATE()) DAY) " +
                        ") AS sub " +
                        "WHERE manufacturer IS NOT NULL " +
                        "GROUP BY manufacturer")
        public List<AreaStatisticsDTO> getAreaStatisticsWeek();

        // @Select("SELECT COALESCE(q.installation_location, n.e_area) AS
        // manufacturer,COUNT(*) as count FROM manage_event as e "
        // + " LEFT JOIN manage_equipment q on q.equipment_id = e.equipment_id AND
        // q.deleted = 0 "
        // + " LEFT JOIN manage_environment n on n.environment_id = e.environment_id AND
        // n.deleted = 0"
        // + " WHERE e.create_time >= CURDATE() - INTERVAL 1 MONTH"
        // + " AND manufacturer IS NOT NULL"
        // + " GROUP BY COALESCE(q.installation_location, n.e_area)")
        @Select("SELECT manufacturer, COUNT(*) AS count " +
                        "FROM ( " +
                        "    SELECT COALESCE(q.installation_location, n.e_area) AS manufacturer " +
                        "    FROM manage_event AS e " +
                        "    LEFT JOIN manage_equipment q ON q.equipment_id = e.equipment_id " +
                        "    LEFT JOIN manage_environment n ON n.environment_id = e.environment_id " +
                        "    WHERE e.create_time >= CURDATE() - INTERVAL 1 MONTH " +
                        ") AS sub " +
                        "WHERE manufacturer IS NOT NULL " +
                        "GROUP BY manufacturer")
        public List<AreaStatisticsDTO> getAreaStatisticsMonth();

        // @Select("SELECT COALESCE(q.installation_location, n.e_area) AS
        // manufacturer,COUNT(*) as count FROM manage_event as e "
        // + " LEFT JOIN manage_equipment q on q.equipment_id = e.equipment_id "
        // + " LEFT JOIN manage_environment n on n.environment_id = e.environment_id"
        // // + " WHERE YEAR(e.create_time) = YEAR(CURDATE())"
        // + " WHERE e.create_time >= DATE_SUB(CURDATE(), INTERVAL 11 MONTH)"
        // + " AND e.create_time <= LAST_DAY(CURDATE())"
        // + " AND manufacturer IS NOT NULL"
        // + " GROUP BY COALESCE(q.installation_location, n.e_area)")
        @Select("SELECT manufacturer, COUNT(*) AS count " +
                        "FROM ( " +
                        "    SELECT COALESCE(q.installation_location, n.e_area) AS manufacturer " +
                        "    FROM manage_event AS e " +
                        "    LEFT JOIN manage_equipment q ON q.equipment_id = e.equipment_id " +
                        "    LEFT JOIN manage_environment n ON n.environment_id = e.environment_id " +
                        "    WHERE e.create_time >= DATE_SUB(CURDATE(), INTERVAL 11 MONTH) " +
                        "    AND e.create_time <= LAST_DAY(CURDATE()) " +
                        ") AS sub " +
                        "WHERE manufacturer IS NOT NULL " +
                        "GROUP BY manufacturer")
        public List<AreaStatisticsDTO> getAreaStatisticsYear();

        @Select("SELECT type as name,COUNT(*) as value from manage_event"
                        + " GROUP BY type")
        public List<AllEventEchartDTO> getAllEventEchart();

        @Select("SELECT q.installation_location as name, COUNT(*) as value FROM manage_event as e LEFT JOIN manage_equipment q ON q.equipment_id = e.equipment_id"
                        + " WHERE e.equipment_id > 0"
                        + " GROUP BY q.installation_location")
        List<AllEventEchartDTO> getAllEquipmentAreaEchart();

        @Select("SELECT q.e_area as name, COUNT(*) as value FROM manage_event as e LEFT JOIN manage_environment q ON q.environment_id = e.environment_id"
                        + " WHERE e.environment_id > 0"
                        + " GROUP BY q.e_area")
        List<AllEventEchartDTO> getAllEnvironmentAreaEchart();

        @Select("SELECT installation_location as name, COUNT(*) as value from manage_equipment where equipment_id in (select equipment_id from manage_craft_node_equipment where craft_node_id in (select craft_node_id from manage_event where craft_node_id is not null)) group by installation_location")
        List<AllEventEchartDTO> getGongYiJieDianAreaEchart();

        @Select("SELECT level as name, COUNT(*) as value from manage_event where type = '工艺节点报警' and DATE(create_time) = CURDATE() group by level")
        List<AllEventEchartDTO> getGongYiJieDianTodayAlarmCount();

        @Select("SELECT unit_name from manage_environment where deleted = 0 group by unit_name")
        List<String> getEnvironmentUnitAll();
}
