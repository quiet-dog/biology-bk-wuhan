package com.biology.domain.manage.equipment.model;

import org.springframework.stereotype.Component;
import com.biology.common.exception.ApiException;
import com.biology.common.exception.error.ErrorCode.Business;
import com.biology.domain.manage.equipment.db.EquipmentInspectionRecordEntity;
import com.biology.domain.manage.equipment.db.EquipmentInspectionRecordService;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class EquipmentInspectionRecordFactory {

    private final EquipmentInspectionRecordService equipmentInspectionRecordService;

    public EquipmentInspectionRecordModel create() {
        return new EquipmentInspectionRecordModel(equipmentInspectionRecordService);
    }

    public EquipmentInspectionRecordModel loadById(Long recordId) {
        EquipmentInspectionRecordEntity entity = equipmentInspectionRecordService.getById(recordId);
        if (entity == null) {
            throw new ApiException(Business.COMMON_OBJECT_NOT_FOUND, recordId, "检修记录");
        }
        return new EquipmentInspectionRecordModel(entity, equipmentInspectionRecordService);
    }
} 