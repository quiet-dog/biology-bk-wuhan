package com.biology.domain.manage.equipment.model;

import com.biology.domain.manage.equipment.command.AddEquipmentCommand;
import com.biology.domain.manage.equipment.command.UpdateEquipmentCommand;
import com.biology.domain.manage.equipment.db.EquipmentEntity;
import com.biology.domain.manage.equipment.db.EquipmentService;
import cn.hutool.core.bean.BeanUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class EquipmentModel extends EquipmentEntity {

    private EquipmentService equipmentService;

    public EquipmentModel(EquipmentService equipmentService) {
        this.equipmentService = equipmentService;
    }

    public EquipmentModel(EquipmentEntity entity, EquipmentService equipmentService) {
        this(equipmentService);
        if (entity != null) {
            BeanUtil.copyProperties(entity, this);
        }
    }

    public void loadAddCommand(AddEquipmentCommand command) {
        if (command != null) {
            BeanUtil.copyProperties(command, this, "equipmentId");
        }
    }

    public void loadUpdateCommand(UpdateEquipmentCommand command) {
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
