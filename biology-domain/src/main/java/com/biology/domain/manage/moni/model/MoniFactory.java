package com.biology.domain.manage.moni.model;

import org.springframework.stereotype.Component;

import com.biology.common.exception.ApiException;
import com.biology.common.exception.error.ErrorCode.Business;
import com.biology.domain.manage.materials.db.MaterialsEntity;
import com.biology.domain.manage.materials.model.MaterialsModel;
import com.biology.domain.manage.moni.db.MoniEntity;
import com.biology.domain.manage.moni.db.MoniService;
import com.biology.domain.manage.moni.db.MoniThresholdService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MoniFactory {
    private final MoniService moniService;

    private final MoniThresholdService moniThresholdService;

    public MoniModel create() {
        return new MoniModel(moniService, moniThresholdService);
    }

    public MoniModel loadById(Long moniId) {
        MoniEntity moniModel = moniService.getById(moniId);
        if (moniModel == null) {
            throw new ApiException(Business.COMMON_OBJECT_NOT_FOUND, moniId, "模拟");
        }
        return new MoniModel(moniModel, moniService, moniThresholdService);
    }
}
