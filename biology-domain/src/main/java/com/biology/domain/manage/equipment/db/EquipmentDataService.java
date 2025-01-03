package com.biology.domain.manage.equipment.db;

import com.baomidou.mybatisplus.extension.service.IService;
import com.biology.domain.manage.equipment.dto.EquipmentDataStockEchartDTO;

public interface EquipmentDataService extends IService<EquipmentDataEntity> {
    EquipmentDataEntity getById(Long equipmentId);

    public EquipmentDataStockEchartDTO getEquipmentDataStockDay(Long threshold);
}