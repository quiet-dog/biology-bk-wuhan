package com.biology.domain.manage.equipment.db;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.extension.service.IService;
import com.biology.domain.manage.equipment.dto.EquipmentDataStockEchartDTO;
import com.biology.domain.manage.equipment.dto.TotalTimeDTO;

public interface EquipmentDataService extends IService<EquipmentDataEntity> {
    EquipmentDataEntity getById(Long equipmentId);

    public EquipmentDataStockEchartDTO getEquipmentDataStockDay(Long threshold);

    public TotalTimeDTO getTotalTime(Long equipmentId);
}