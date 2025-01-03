package com.biology.domain.manage.healthy.db;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.biology.domain.manage.healthy.dto.HealthyAlarmStock;

public interface HealthyAlarmMapper extends BaseMapper<HealthyAlarmEntity> {

        @Select("SELECT DATE_FORMAT(create_time, '%m-%d') AS time,count(*) as count,type FROM manage_personnel_healthy_alarm"
                        + " WHERE create_time >= CURDATE() - INTERVAL 6 DAY AND create_time < CURDATE() + INTERVAL 1 DAY"
                        + " GROUP BY DATE_FORMAT(create_time, '%m-%d'),type")
        public List<HealthyAlarmStock> getHealthyAlarmTypeStaticWeek();

        @Select("SELECT DATE_FORMAT(create_time, '%m-%d') AS time,count(*) as count,type FROM manage_personnel_healthy_alarm"
                        + " WHERE create_time >= CURDATE() - INTERVAL 1 MONTH AND create_time < CURDATE() + INTERVAL 1 DAY"
                        + " GROUP BY DATE_FORMAT(create_time, '%m-%d'),type")
        public List<HealthyAlarmStock> getHealthyAlarmTypeStaticMonth();

        @Select("SELECT DATE_FORMAT(create_time, '%Y-%m') AS time,count(*) as count,type FROM manage_personnel_healthy_alarm"
                        + " WHERE create_time >= DATE_SUB(CURDATE(), INTERVAL 11 MONTH) AND create_time < LAST_DAY(CURDATE()) + INTERVAL 1 DAY"
                        + " GROUP BY DATE_FORMAT(create_time, '%Y-%m'),type")
        public List<HealthyAlarmStock> getHealthyAlarmTypeStaticYear();

}
