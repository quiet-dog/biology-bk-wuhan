package com.biology.domain.manage.equipment.model;

import com.biology.domain.manage.equipment.command.AddEquipmentDataCommand;
import com.biology.domain.manage.equipment.command.UpdateEquipmentDataCommand;
import com.biology.domain.manage.equipment.db.EquipmentDataEntity;
import com.biology.domain.manage.equipment.db.EquipmentDataService;
import cn.hutool.core.bean.BeanUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class EquipmentDataModel extends EquipmentDataEntity {

    private EquipmentDataService equipmentDataService;

    public EquipmentDataModel(EquipmentDataService equipmentDataService) {
        this.equipmentDataService = equipmentDataService;
    }

    public EquipmentDataModel(EquipmentDataEntity entity,
            EquipmentDataService equipmentDataService) {
        this(equipmentDataService);
        if (entity != null) {
            BeanUtil.copyProperties(entity, this);
        }
    }

    public void loadAddCommand(AddEquipmentDataCommand command) {
        if (command != null) {
            BeanUtil.copyProperties(command, this, "equipmentDataId");
        }
    }

    public void loadUpdateCommand(UpdateEquipmentDataCommand command) {
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