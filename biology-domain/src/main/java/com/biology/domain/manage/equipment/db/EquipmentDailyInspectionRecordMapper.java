package com.biology.domain.manage.equipment.db;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.biology.domain.manage.equipment.dto.EquipmentDataStockDTO;
import com.biology.domain.manage.equipment.dto.EquipmentDataStockEchartDTO;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface EquipmentDailyInspectionRecordMapper extends BaseMapper<EquipmentDailyInspectionRecordEntity> {

    @Select("SELECT SUM(anomaly_count) as data,inspection_date as time FROM manage_equipment_daily_inspection_record"
            + " WHERE DATE(inspection_date) BETWEEN #{startTime} AND #{endTime}"
            + " AND deleted = 0"
            + " GROUP BY inspection_date")
    List<EquipmentDataStockDTO> getCishu(@Param("startTime") String startTime,
            @Param("endTime") String endTime);
}