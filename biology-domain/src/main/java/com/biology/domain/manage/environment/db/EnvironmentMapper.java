package com.biology.domain.manage.environment.db;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.biology.domain.manage.environment.dto.EnvironmentStatisticsDTO;
import com.biology.domain.manage.environment.query.DayStatisticsQuery;

public interface EnvironmentMapper extends BaseMapper<EnvironmentEntity> {

        @Select("SELECT unit,DATE_FORMAT(create_time, '%H:%i:%s') AS data_time,value FROM manage_environment WHERE environment_id = #{environmentId}"
                        + " GROUP BY unit,data_time"
                        + " ORDER BY unit,data_time")
        public List<EnvironmentStatisticsDTO> getDayStatistics(Long environmentId);

        @Select("SELECT e_area FROM manage_environment"
                        + " GROUP BY e_area")
        public List<String> getAllArea();

        @Select("SELECT description FROM manage_environment"
                        + " GROUP BY description")
        public List<String> getAllDes();

        @Select("SELECT unit_name FROM manage_environment"
                        + " GROUP BY unit_name")
        public List<String> getAllUnitName();
}
