package com.biology.domain.manage.emergencyEvent.db;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.biology.domain.manage.emergencyEvent.dto.EmergenctHandleDTO;
import com.biology.domain.manage.event.dto.EnvironmentStock;

// import io.lettuce.core.dynamic.annotation.Param;
import org.apache.ibatis.annotations.Param;

public interface EmergencyEventMapper extends BaseMapper<EmergencyEventEntity> {

        @Select("SELECT COUNT(emergency_event_id) as count,DATE_FORMAT(create_time, '%m') as time from manage_emergency_event"
                        + " WHERE type = #{eventType} "
                        + " AND YEAR(create_time) = YEAR(NOW())"
                        + " GROUP BY DATE_FORMAT(create_time, '%m')"
                        + " ORDER BY DATE_FORMAT(create_time, '%m')")
        List<EnvironmentStock> getStock(@Param("eventType") String eventType);

        @Select("SELECT DATE_FORMAT(create_time, '%m') AS time,"
                        + " SUM(CASE WHEN status = TRUE THEN 1 ELSE 0 END) AS handled,"
                        + " SUM(CASE WHEN status = FALSE THEN 1 ELSE 0 END) AS un_handled"
                        + " FROM"
                        + " manage_emergency_event"
                        + " WHERE type = #{eventType}"
                        + " AND YEAR(create_time) = YEAR(NOW())"
                        + " GROUP BY"
                        + " DATE_FORMAT(create_time, '%m')"
                        + " ORDER BY"
                        + " time")
        List<EmergenctHandleDTO> getStockByHandle(@Param("eventType") String eventType);
}
