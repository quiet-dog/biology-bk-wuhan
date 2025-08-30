package com.biology.domain.manage.kongTiaoData.model;

import org.springframework.stereotype.Component;

import com.biology.common.exception.ApiException;
import com.biology.common.exception.error.ErrorCode.Business;
import com.biology.domain.manage.kongTiaoData.db.KongTiaoDataEntity;
import com.biology.domain.manage.kongTiaoData.db.KongTiaoDataService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class KongTiaoDataFactory {
    private final KongTiaoDataService kongTiaoDataService;

    public KongTiaoDataModel create() {
        return new KongTiaoDataModel(kongTiaoDataService);
    }

    public KongTiaoDataModel loadById(Long id) {
        KongTiaoDataEntity byId = kongTiaoDataService.getById(id);
        if (byId == null) {
            throw new ApiException(Business.COMMON_OBJECT_NOT_FOUND, id, "接收");
        }
        return new KongTiaoDataModel(byId, kongTiaoDataService);
    }
}
