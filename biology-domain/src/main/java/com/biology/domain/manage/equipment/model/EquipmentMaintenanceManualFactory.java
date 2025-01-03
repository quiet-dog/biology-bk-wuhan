package com.biology.domain.manage.equipment.model;

import org.springframework.stereotype.Component;
import com.biology.common.exception.ApiException;
import com.biology.common.exception.error.ErrorCode.Business;
import com.biology.domain.manage.equipment.db.EquipmentMaintenanceManualEntity;
import com.biology.domain.manage.equipment.db.EquipmentMaintenanceManualService;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class EquipmentMaintenanceManualFactory {

    private final EquipmentMaintenanceManualService equipmentMaintenanceManualService;

    public EquipmentMaintenanceManualModel create() {
        return new EquipmentMaintenanceManualModel(equipmentMaintenanceManualService);
    }

    public EquipmentMaintenanceManualModel loadById(Long manualId) {
        EquipmentMaintenanceManualEntity entity = equipmentMaintenanceManualService.getById(manualId);
        if (entity == null) {
            throw new ApiException(Business.COMMON_OBJECT_NOT_FOUND, manualId, "维修手册");
        }
        return new EquipmentMaintenanceManualModel(entity, equipmentMaintenanceManualService);
    }
} 