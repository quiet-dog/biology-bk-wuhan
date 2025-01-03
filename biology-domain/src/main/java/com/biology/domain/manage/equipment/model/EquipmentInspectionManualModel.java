package com.biology.domain.manage.equipment.model;

import com.biology.domain.manage.equipment.command.AddEquipmentInspectionManualCommand;
import com.biology.domain.manage.equipment.command.UpdateEquipmentInspectionManualCommand;
import com.biology.domain.manage.equipment.db.EquipmentInspectionManualEntity;
import com.biology.domain.manage.equipment.db.EquipmentInspectionManualService;
import cn.hutool.core.bean.BeanUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class EquipmentInspectionManualModel extends EquipmentInspectionManualEntity {

    private EquipmentInspectionManualService equipmentInspectionManualService;

    public EquipmentInspectionManualModel(EquipmentInspectionManualService equipmentInspectionManualService) {
        this.equipmentInspectionManualService = equipmentInspectionManualService;
    }

    public EquipmentInspectionManualModel(EquipmentInspectionManualEntity entity,
            EquipmentInspectionManualService equipmentInspectionManualService) {
        this(equipmentInspectionManualService);
        if (entity != null) {
            BeanUtil.copyProperties(entity, this);
        }
    }

    public void loadAddCommand(AddEquipmentInspectionManualCommand command) {
        if (command != null) {
            BeanUtil.copyProperties(command, this, "manualId");
        }
    }

    public void loadUpdateCommand(UpdateEquipmentInspectionManualCommand command) {
        if (command != null) {
            BeanUtil.copyProperties(command, this, "manualId");
        }
    }

    public boolean insert() {
        return super.insert();
    }

    public boolean update() {
        return super.updateById();
    }
}