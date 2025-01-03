package com.biology.domain.manage.sop.model;

import org.springframework.stereotype.Component;

import com.biology.common.exception.ApiException;
import com.biology.common.exception.error.ErrorCode.Business;
import com.biology.domain.manage.sop.db.SopEntity;
import com.biology.domain.manage.sop.db.SopEquipmentService;
import com.biology.domain.manage.sop.db.SopFileService;
import com.biology.domain.manage.sop.db.SopService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SopFactory {

    private final SopService sopService;

    private final SopFileService sopFileService;

    private final SopEquipmentService sopEquipmentService;

    public SopModel create() {
        return new SopModel(sopService, sopFileService, sopEquipmentService);
    }

    public SopModel loadById(Long sopId) {

        SopEntity sopModel = sopService.getById(sopId);

        if (sopModel == null) {
            throw new ApiException(Business.COMMON_OBJECT_NOT_FOUND, sopId, "SOP");
        }

        return new SopModel(sopModel, sopService, sopFileService, sopEquipmentService);
    }
}
