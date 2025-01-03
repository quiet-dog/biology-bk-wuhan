package com.biology.domain.manage.equipment.db;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.biology.domain.manage.equipment.dto.EquipmentDataStockDTO;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface EquipmentDataMapper extends BaseMapper<EquipmentDataEntity> {

    @Select("select AVG(equipment_data) AS data,CONCAT(LPAD(FLOOR(HOUR(create_time) / 1) * 1, 2, '0'), ':', LPAD(FLOOR(MINUTE(create_time) / 30) * 30, 2, '0')) AS time from manage_equipment_data"
            + " WHERE create_time >= CURDATE() AND threshold_id = #{thresholdId}"
            + " GROUP BY time"
            + " ORDER BY time")
    public List<EquipmentDataStockDTO> getEquipmentDataStockDay(@Param("thresholdId") Long thresholdId);
}