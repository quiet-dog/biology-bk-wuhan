package com.biology.domain.manage.equipment.model;

import org.springframework.stereotype.Component;
import com.biology.common.exception.ApiException;
import com.biology.common.exception.error.ErrorCode.Business;
import com.biology.domain.manage.equipment.db.EquipmentInspectionManualEntity;
import com.biology.domain.manage.equipment.db.EquipmentInspectionManualService;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class EquipmentInspectionManualFactory {

    private final EquipmentInspectionManualService equipmentInspectionManualService;

    public EquipmentInspectionManualModel create() {
        return new EquipmentInspectionManualModel(equipmentInspectionManualService);
    }

    public EquipmentInspectionManualModel loadById(Long manualId) {
        EquipmentInspectionManualEntity entity = equipmentInspectionManualService.getById(manualId);
        if (entity == null) {
            throw new ApiException(Business.COMMON_OBJECT_NOT_FOUND, manualId, "检修手册");
        }
        return new EquipmentInspectionManualModel(entity, equipmentInspectionManualService);
    }
} 