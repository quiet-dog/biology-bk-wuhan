package com.biology.domain.manage.equipment.model;

import org.springframework.stereotype.Component;
import com.biology.common.exception.ApiException;
import com.biology.common.exception.error.ErrorCode.Business;
import com.biology.domain.manage.equipment.db.EquipmentEntity;
import com.biology.domain.manage.equipment.db.EquipmentService;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class EquipmentFactory {

    private final EquipmentService equipmentService;

    public EquipmentModel create() {
        return new EquipmentModel(equipmentService);
    }

    public EquipmentModel loadById(Long equipmentId) {
        EquipmentEntity entity = equipmentService.getById(equipmentId);
        if (entity == null) {
            throw new ApiException(Business.COMMON_OBJECT_NOT_FOUND, equipmentId, "设备档案");
        }
        return new EquipmentModel(entity, equipmentService);
    }

    public EquipmentModel loadByCode(String equipmentCode) {
        EquipmentEntity entity = equipmentService.getByEquipmentCode(equipmentCode);
        if (entity == null) {
            throw new ApiException(Business.COMMON_OBJECT_NOT_FOUND, equipmentCode, "设备档案");
        }
        return new EquipmentModel(entity, equipmentService);
    }
}
