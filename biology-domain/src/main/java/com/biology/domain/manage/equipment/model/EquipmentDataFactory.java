package com.biology.domain.manage.equipment.model;

import org.springframework.stereotype.Component;
import com.biology.common.exception.ApiException;
import com.biology.common.exception.error.ErrorCode.Business;
import com.biology.domain.manage.equipment.db.EquipmentDataEntity;
import com.biology.domain.manage.equipment.db.EquipmentDataService;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class EquipmentDataFactory {

    private final EquipmentDataService equipmentDataService;

    public EquipmentDataModel create() {
        return new EquipmentDataModel(equipmentDataService);
    }

    public EquipmentDataModel loadById(Long equipmentId) {
        EquipmentDataEntity entity = equipmentDataService.getById(equipmentId);
        if (entity == null) {
            throw new ApiException(Business.COMMON_OBJECT_NOT_FOUND, equipmentId, "设备数据");
        }
        return new EquipmentDataModel(entity, equipmentDataService);
    }
} 