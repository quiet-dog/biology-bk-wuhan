package com.biology.domain.manage.equipment.model;

import com.biology.domain.manage.equipment.command.AddEquipmentRepairRecordCommand;
import com.biology.domain.manage.equipment.command.UpdateEquipmentRepairRecordCommand;
import com.biology.domain.manage.equipment.db.EquipmentRepairRecordEntity;
import com.biology.domain.manage.equipment.db.EquipmentRepairRecordService;
import cn.hutool.core.bean.BeanUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class EquipmentRepairRecordModel extends EquipmentRepairRecordEntity {

    private EquipmentRepairRecordService equipmentRepairRecordService;

    public EquipmentRepairRecordModel(EquipmentRepairRecordService equipmentRepairRecordService) {
        this.equipmentRepairRecordService = equipmentRepairRecordService;
    }

    public EquipmentRepairRecordModel(EquipmentRepairRecordEntity entity,
            EquipmentRepairRecordService equipmentRepairRecordService) {
        this(equipmentRepairRecordService);
        if (entity != null) {
            BeanUtil.copyProperties(entity, this);
        }
    }

    public void loadAddCommand(AddEquipmentRepairRecordCommand command) {
        if (command != null) {
            BeanUtil.copyProperties(command, this, "recordId");
        }
    }

    public void loadUpdateCommand(UpdateEquipmentRepairRecordCommand command) {
        if (command != null) {
            BeanUtil.copyProperties(command, this);
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