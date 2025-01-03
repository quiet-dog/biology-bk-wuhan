package com.biology.domain.manage.equipment.db;

import com.biology.domain.manage.equipment.query.EquipmentDataEchartDTO;
import com.biology.domain.manage.event.dto.EventEchartDTO;
import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;

public interface EquipmentRepairRecordService extends IService<EquipmentRepairRecordEntity> {
    EquipmentRepairRecordEntity getById(Long recordId);

    EventEchartDTO repairRecordByTime(EquipmentDataEchartDTO query);
}
