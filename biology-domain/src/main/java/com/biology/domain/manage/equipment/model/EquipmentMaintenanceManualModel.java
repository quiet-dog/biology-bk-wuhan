package com.biology.domain.manage.equipment.model;

import com.biology.domain.manage.equipment.command.AddEquipmentMaintenanceManualCommand;
import com.biology.domain.manage.equipment.command.UpdateEquipmentMaintenanceManualCommand;
import com.biology.domain.manage.equipment.db.EquipmentMaintenanceManualEntity;
import com.biology.domain.manage.equipment.db.EquipmentMaintenanceManualService;
import cn.hutool.core.bean.BeanUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class EquipmentMaintenanceManualModel extends EquipmentMaintenanceManualEntity {

    private EquipmentMaintenanceManualService equipmentMaintenanceManualService;

    public EquipmentMaintenanceManualModel(EquipmentMaintenanceManualService equipmentMaintenanceManualService) {
        this.equipmentMaintenanceManualService = equipmentMaintenanceManualService;
    }

    public EquipmentMaintenanceManualModel(EquipmentMaintenanceManualEntity entity,
            EquipmentMaintenanceManualService equipmentMaintenanceManualService) {
        this(equipmentMaintenanceManualService);
        if (entity != null) {
            BeanUtil.copyProperties(entity, this);
        }
    }

    public void loadAddCommand(AddEquipmentMaintenanceManualCommand command) {
        if (command != null) {
            BeanUtil.copyProperties(command, this, "manualId");
        }
    }

    public void loadUpdateCommand(UpdateEquipmentMaintenanceManualCommand command) {
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

    public boolean deleteById() {
        return super.deleteById();
    }
}