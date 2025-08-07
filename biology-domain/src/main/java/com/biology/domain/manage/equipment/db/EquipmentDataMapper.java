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

        @Select("select AVG(equipment_data) AS data,CONCAT(LPAD(FLOOR(HOUR(create_time) / 1) * 1, 2, '0'), ':', LPAD(FLOOR(MINUTE(create_time) / 30) * 30, 2, '0')) AS time from manage_equipment_data"
                        + " WHERE DATE(create_time) = CURDATE() AND threshold_id = #{thresholdId}"
                        + " GROUP BY time"
                        + " ORDER BY time")
        public List<EquipmentDataStockDTO> getEquipmentDataStockDay(@Param("thresholdId") Long thresholdId);

        @Select("SELECT SUM(CASE WHEN create_time < 60 THEN create_time ELSE 0 END) AS totalTime"
                        + " FROM (SELECT equipment_id,TIMESTAMPDIFF(MINUTE, LAG(create_time) OVER (PARTITION BY equipment_id ORDER BY create_time), create_time) AS create_time"
                        + " FROM manage_equipment_data WHERE monitoring_indicator = '电压' AND equipment_id = #{equipmentId}"
                        + " ) as subquery")
        public Integer getTotalTime(@Param("equipmentId") Long equipmentId);
}