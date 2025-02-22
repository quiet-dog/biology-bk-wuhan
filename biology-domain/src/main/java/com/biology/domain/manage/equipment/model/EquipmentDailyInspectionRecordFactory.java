package com.biology.domain.manage.equipment.model;

import org.springframework.stereotype.Component;
import com.biology.common.exception.ApiException;
import com.biology.common.exception.error.ErrorCode.Business;
import com.biology.domain.manage.equipment.db.EquipmentDailyInspectionRecordEntity;
import com.biology.domain.manage.equipment.db.EquipmentDailyInspectionRecordService;
import com.biology.domain.manage.notification.db.NotificationService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class EquipmentDailyInspectionRecordFactory {

    private final EquipmentDailyInspectionRecordService equipmentDailyInspectionRecordService;

    private final NotificationService notificationService;

    public EquipmentDailyInspectionRecordModel create() {
        return new EquipmentDailyInspectionRecordModel(equipmentDailyInspectionRecordService, notificationService);
    }

    public EquipmentDailyInspectionRecordModel loadById(Long recordId) {
        EquipmentDailyInspectionRecordEntity entity = equipmentDailyInspectionRecordService.getById(recordId);
        if (entity == null) {
            throw new ApiException(Business.COMMON_OBJECT_NOT_FOUND, recordId, "巡检记录");
        }
        return new EquipmentDailyInspectionRecordModel(entity, equipmentDailyInspectionRecordService,
                notificationService);
    }
}