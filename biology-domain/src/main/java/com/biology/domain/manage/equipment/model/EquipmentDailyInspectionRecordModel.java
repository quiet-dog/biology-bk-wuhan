package com.biology.domain.manage.equipment.model;

import com.biology.domain.manage.equipment.command.AddEquipmentDailyInspectionRecordCommand;
import com.biology.domain.manage.equipment.command.UpdateEquipmentDailyInspectionRecordCommand;
import com.biology.domain.manage.equipment.db.EquipmentDailyInspectionRecordEntity;
import com.biology.domain.manage.equipment.db.EquipmentDailyInspectionRecordService;
import cn.hutool.core.bean.BeanUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class EquipmentDailyInspectionRecordModel extends EquipmentDailyInspectionRecordEntity {

    private EquipmentDailyInspectionRecordService equipmentDailyInspectionRecordService;

    public EquipmentDailyInspectionRecordModel(
            EquipmentDailyInspectionRecordService equipmentDailyInspectionRecordService) {
        this.equipmentDailyInspectionRecordService = equipmentDailyInspectionRecordService;
    }

    public EquipmentDailyInspectionRecordModel(EquipmentDailyInspectionRecordEntity entity,
            EquipmentDailyInspectionRecordService equipmentDailyInspectionRecordService) {
        this(equipmentDailyInspectionRecordService);
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

    public boolean insert() {
        return super.insert();
    }

    public boolean update() {
        return super.updateById();
    }

    public boolean deleteById() {
        return super.deleteById();
    }
}
