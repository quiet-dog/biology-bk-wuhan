package com.biology.domain.manage.materials.model;

import org.springframework.beans.BeanUtils;

import com.biology.domain.manage.materials.command.AddWarehouseCommand;
import com.biology.domain.manage.materials.db.WarehouseEntity;
import com.biology.domain.manage.materials.db.WarehouseService;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class WarehouseModel extends WarehouseEntity {

    private WarehouseService warehouseService;

    public WarehouseModel(WarehouseService warehouseService) {
        this.warehouseService = warehouseService;
    }

    public WarehouseModel(WarehouseEntity entity, WarehouseService warehouseService) {
        this(warehouseService);
        if (entity != null) {
            BeanUtils.copyProperties(entity, this);
        }
    }

    public void loadAddMaterialsCommand(AddWarehouseCommand command) {
        if (command != null) {
            BeanUtils.copyProperties(command, this, "warehouseId");
        }
    }

}
