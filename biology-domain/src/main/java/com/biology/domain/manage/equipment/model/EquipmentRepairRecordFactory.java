package com.biology.domain.manage.equipment.model;

import org.springframework.stereotype.Component;
import com.biology.common.exception.ApiException;
import com.biology.common.exception.error.ErrorCode.Business;
import com.biology.domain.manage.equipment.db.EquipmentRepairRecordEntity;
import com.biology.domain.manage.equipment.db.EquipmentRepairRecordService;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class EquipmentRepairRecordFactory {

    private final EquipmentRepairRecordService equipmentRepairRecordService;

    public EquipmentRepairRecordModel create() {
        return new EquipmentRepairRecordModel(equipmentRepairRecordService);
    }

    public EquipmentRepairRecordModel loadById(Long recordId) {
        EquipmentRepairRecordEntity entity = equipmentRepairRecordService.getById(recordId);
        if (entity == null) {
            throw new ApiException(Business.COMMON_OBJECT_NOT_FOUND, recordId, "维修记录");
        }
        return new EquipmentRepairRecordModel(entity, equipmentRepairRecordService);
    }
} 