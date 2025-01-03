package com.biology.domain.manage.materials.model;

import org.springframework.stereotype.Component;

import com.biology.common.exception.ApiException;
import com.biology.common.exception.error.ErrorCode.Business;
import com.biology.domain.manage.event.db.EventService;
import com.biology.domain.manage.materials.db.MaterialsEntity;
import com.biology.domain.manage.materials.db.MaterialsService;
import com.biology.domain.manage.materials.db.MaterialsValueService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MaterialsFactory {

    private final MaterialsService materialsService;

    private final MaterialsValueService materialsValueService;

    private final EventService eventService;

    public MaterialsModel create() {
        return new MaterialsModel(materialsService, materialsValueService, eventService);
    }

    public MaterialsModel loadById(Long materialsId) {

        MaterialsEntity materialsModel = materialsService.getById(materialsId);
        if (materialsModel == null) {
            throw new ApiException(Business.COMMON_OBJECT_NOT_FOUND, materialsId, "物料");
        }

        return new MaterialsModel(materialsModel, materialsService, materialsValueService, eventService);
    }


}
