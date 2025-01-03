package com.biology.domain.manage.emergencyEvent.db;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.biology.domain.manage.event.dto.EnvironmentStock;

public interface EmergencyEventMapper extends BaseMapper<EmergencyEventEntity> {

    @Select("SELECT COUNT(*) as count,DATE_FORMAT(create_time, '%m') as time from manage_emergency_event"
            + " WHERE type = #{eventType} "
            + " AND YEAR(create_time) = YEAR(NOW())"
            + " GROUP BY DATE_FORMAT(create_time, '%m')"
            + " ORDER BY DATE_FORMAT(create_time, '%m')")
    List<EnvironmentStock> getStock(String eventType);
}
