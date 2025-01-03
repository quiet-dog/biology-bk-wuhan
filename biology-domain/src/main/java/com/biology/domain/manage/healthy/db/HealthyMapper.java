package com.biology.domain.manage.healthy.db;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.biology.domain.manage.healthy.dto.HealthyAlarmStock;
import com.biology.domain.manage.healthy.dto.HealthyDTO;
import com.biology.domain.manage.healthy.dto.HealthyStock;

public interface HealthyMapper extends BaseMapper<HealthyEntity> {

        @Select("select temperature as data, DATE_FORMAT(create_time, '%H:%i:%s') as time  from manage_personnel_healthy where personnel_id = #{personnelId}"
                        + " AND DATE(create_time) = CURDATE()"
                        + " AND temperature is not null"
                        + " AND temperature != 0")
        public List<HealthyStock> getTemperatureWeek(Long personnelId);

        @Select("select AVG(temperature) as data, DATE_FORMAT(create_time, '%Y-%m-%d') as time  from manage_personnel_healthy where personnel_id = #{personnelId}"
                        + " AND create_time >= CURDATE() - INTERVAL 1 MONTH AND create_time <= NOW()"
                        + " AND temperature is not null"
                        + " GROUP BY DATE_FORMAT(create_time, '%Y-%m-%d')")
        public List<HealthyStock> getTemperatureMonth(Long personnelId);

        @Select("select AVG(temperature) as data, DATE_FORMAT(create_time, '%m') as time  from manage_personnel_healthy where personnel_id = #{personnelId}"
                        + " AND YEAR(create_time) = YEAR(CURDATE())"
                        + " AND temperature is not null"
                        + " GROUP BY DATE_FORMAT(create_time, '%m')")
        public List<HealthyStock> getTemperatureYear(Long personnelId);

        // 收缩压
        @Select("select high_blood_pressure as data, DATE_FORMAT(create_time, '%H:%i:%s') as time  from manage_personnel_healthy where personnel_id = #{personnelId}"
                        + " AND YEAR(create_time) = YEAR(CURDATE())"
                        + " AND high_blood_pressure is not null"
                        + " AND high_blood_pressure != 0")
        public List<HealthyStock> getHighBloodPressureWeek(Long personnelId);

        @Select("select AVG(high_blood_pressure) as data, DATE_FORMAT(create_time, '%Y-%m-%d') as time  from manage_personnel_healthy where personnel_id = #{personnelId}"
                        + " AND create_time >= CURDATE() - INTERVAL 1 MONTH AND create_time <= NOW()"
                        + " AND high_blood_pressure is not null"
                        + " GROUP BY DATE_FORMAT(create_time, '%Y-%m-%d')")
        public List<HealthyStock> getHighBloodPressureMonth(Long personnelId);

        @Select("select AVG(high_blood_pressure) as data, DATE_FORMAT(create_time, '%m') as time  from manage_personnel_healthy where personnel_id = #{personnelId}"
                        + " AND YEAR(create_time) = YEAR(CURDATE())"
                        + " AND high_blood_pressure is not null"
                        + " GROUP BY DATE_FORMAT(create_time, '%m')")
        public List<HealthyStock> getHighBloodPressureYear(Long personnelId);

        // 舒张压
        @Select("select low_blood_pressure as data, DATE_FORMAT(create_time, '%H:%i:%s') as time  from manage_personnel_healthy where personnel_id = #{personnelId}"
                        + " AND YEAR(create_time) = YEAR(CURDATE())"
                        + " AND low_blood_pressure is not null"
                        + " AND low_blood_pressure != 0")
        public List<HealthyStock> getLowBloodPressureWeek(Long personnelId);

        @Select("select AVG(low_blood_pressure) as data, DATE_FORMAT(create_time, '%Y-%m-%d') as time  from manage_personnel_healthy where personnel_id = #{personnelId}"
                        + " AND create_time >= CURDATE() - INTERVAL 1 MONTH AND create_time <= NOW()"
                        + " AND low_blood_pressure is not null"
                        + " GROUP BY DATE_FORMAT(create_time, '%Y-%m-%d')")
        public List<HealthyStock> getLowBloodPressureMonth(Long personnelId);

        @Select("select AVG(low_blood_pressure) as data, DATE_FORMAT(create_time, '%m') as time  from manage_personnel_healthy where personnel_id = #{personnelId}"
                        + " AND YEAR(create_time) = YEAR(CURDATE())"
                        + " AND low_blood_pressure is not null"
                        + " GROUP BY DATE_FORMAT(create_time, '%m')")
        public List<HealthyStock> getLowBloodPressureYear(Long personnelId);

        // 心率
        @Select("select heart_rate as data, DATE_FORMAT(create_time, '%H:%i:%s') as time  from manage_personnel_healthy where personnel_id = #{personnelId}"
                        + " AND YEAR(create_time) = YEAR(CURDATE())"
                        + " AND heart_rate is not null"
                        + " AND heart_rate != 0")
        public List<HealthyStock> getHeartRateWeek(Long personnelId);

        @Select("select AVG(heart_rate) as data, DATE_FORMAT(create_time, '%Y-%m-%d') as time  from manage_personnel_healthy where personnel_id = #{personnelId}"
                        + " AND create_time >= CURDATE() - INTERVAL 1 MONTH AND create_time <= NOW()"
                        + " AND heart_rate is not null"
                        + " GROUP BY DATE_FORMAT(create_time, '%Y-%m-%d')")
        public List<HealthyStock> getHeartRateMonth(Long personnelId);

        @Select("select AVG(heart_rate) as data, DATE_FORMAT(create_time, '%m') as time  from manage_personnel_healthy where personnel_id = #{personnelId}"
                        + " AND YEAR(create_time) = YEAR(CURDATE())"
                        + " AND heart_rate is not null"
                        + " GROUP BY DATE_FORMAT(create_time, '%m')")
        public List<HealthyStock> getHeartRateYear(Long personnelId);

}
