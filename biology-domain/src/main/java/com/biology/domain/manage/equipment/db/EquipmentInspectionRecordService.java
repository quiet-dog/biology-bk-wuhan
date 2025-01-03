package com.biology.domain.manage.equipment.db;

import com.baomidou.mybatisplus.extension.service.IService;

public interface EquipmentInspectionRecordService extends IService<EquipmentInspectionRecordEntity> {
    EquipmentInspectionRecordEntity getById(Long recordId);
} 