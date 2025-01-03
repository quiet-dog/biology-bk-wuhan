package com.biology.domain.manage.equipment.model;

import com.biology.domain.manage.equipment.command.AddEquipmentInspectionRecordCommand;
import com.biology.domain.manage.equipment.command.UpdateEquipmentInspectionRecordCommand;
import com.biology.domain.manage.equipment.db.EquipmentInspectionRecordEntity;
import com.biology.domain.manage.equipment.db.EquipmentInspectionRecordService;
import cn.hutool.core.bean.BeanUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class EquipmentInspectionRecordModel extends EquipmentInspectionRecordEntity {

    private EquipmentInspectionRecordService equipmentInspectionRecordService;

    public EquipmentInspectionRecordModel(EquipmentInspectionRecordService equipmentInspectionRecordService) {
        this.equipmentInspectionRecordService = equipmentInspectionRecordService;
    }

    public EquipmentInspectionRecordModel(EquipmentInspectionRecordEntity entity,
            EquipmentInspectionRecordService equipmentInspectionRecordService) {
        this(equipmentInspectionRecordService);
        if (entity != null) {
            BeanUtil.copyProperties(entity, this);
        }
    }

    public void loadAddCommand(AddEquipmentInspectionRecordCommand command) {
        if (command != null) {
            BeanUtil.copyProperties(command, this, "recordId");
        }
    }

    public void loadUpdateCommand(UpdateEquipmentInspectionRecordCommand command) {
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