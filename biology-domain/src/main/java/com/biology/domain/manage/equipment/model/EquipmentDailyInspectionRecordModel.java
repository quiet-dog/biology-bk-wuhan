package com.biology.domain.manage.equipment.model;

import java.util.ArrayList;
import java.util.List;

import com.biology.domain.manage.equipment.command.AddEquipmentDailyInspectionRecordCommand;
import com.biology.domain.manage.equipment.command.UpdateEquipmentDailyInspectionRecordCommand;
import com.biology.domain.manage.equipment.db.EquipmentDailyInspectionRecordEntity;
import com.biology.domain.manage.equipment.db.EquipmentDailyInspectionRecordService;
import com.biology.domain.manage.notification.command.AddNotificationCommand;
import com.biology.domain.manage.notification.db.NotificationEntity;
import com.biology.domain.manage.notification.db.NotificationService;

import cn.hutool.core.bean.BeanUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class EquipmentDailyInspectionRecordModel extends EquipmentDailyInspectionRecordEntity {

    private EquipmentDailyInspectionRecordService equipmentDailyInspectionRecordService;

    private NotificationService notificationService;

    private List<Long> inspectorIds;

    public EquipmentDailyInspectionRecordModel(
            EquipmentDailyInspectionRecordService equipmentDailyInspectionRecordService,
            NotificationService notificationService) {
        this.equipmentDailyInspectionRecordService = equipmentDailyInspectionRecordService;
        this.notificationService = notificationService;
    }

    public EquipmentDailyInspectionRecordModel(EquipmentDailyInspectionRecordEntity entity,
            EquipmentDailyInspectionRecordService equipmentDailyInspectionRecordService,
            NotificationService notificationService) {
        this(equipmentDailyInspectionRecordService, notificationService);
        if (entity != null) {
            BeanUtil.copyProperties(entity, this);
        }
    }

    public void loadAddCommand(AddEquipmentDailyInspectionRecordCommand command) {
        if (command != null) {
            BeanUtil.copyProperties(command, this, "recordId");
        }
    }

    public void loadUpdateCommand(UpdateEquipmentDailyInspectionRecordCommand command) {
        if (command != null) {
            BeanUtil.copyProperties(command, this, "recordId");
        }
    }

    // 发送通知

    public boolean sendNotification() {
        AddNotificationCommand command = new AddNotificationCommand();
        List<NotificationEntity> notificationEntities = new ArrayList<>();
        for (Long inspectorId : getInspectorIds()) {
            command.setReceiverId(inspectorId);
            command.setImportance(2);
            command.setNotificationType("提醒");
            command.setNotificationTitle("任务提醒");
            command.setInspectionRecordId(getRecordId());
            command.setNotificationContent(getTaskDescription());
            NotificationEntity notificationEntity = new NotificationEntity();
            BeanUtil.copyProperties(command, notificationEntity);
            notificationEntities.add(notificationEntity);
        }
        if (notificationEntities.size() > 0) {
            notificationService.saveBatch(notificationEntities);
        }
        return true;
    }

    public boolean insert() {
        super.insert();
        sendNotification();
        return true;
    }

    public boolean update() {
        return super.updateById();
    }

    public boolean deleteById() {
        return super.deleteById();
    }
}
