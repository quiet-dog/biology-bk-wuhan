package com.biology.domain.manage.materials.model;

import org.springframework.stereotype.Component;

import com.biology.common.exception.ApiException;
import com.biology.common.exception.error.ErrorCode.Business;
import com.biology.domain.manage.materials.db.WarehouseEntity;
import com.biology.domain.manage.materials.db.WarehouseService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class WarehouseFactory {

    private final WarehouseService warehouseService;

    public WarehouseModel create() {
        return new WarehouseModel(warehouseService);
    }

    public WarehouseModel loadById(Long warehouseId) {

        WarehouseEntity warehouseModel = warehouseService.getById(warehouseId);
        if (warehouseModel == null) {
            throw new ApiException(Business.COMMON_OBJECT_NOT_FOUND, warehouseId, "仓库");
        }

        return new WarehouseModel(warehouseModel, warehouseService);
    }
}
