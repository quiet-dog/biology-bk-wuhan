package com.biology.domain.manage.caiYangData.model;

import org.springframework.stereotype.Component;

import com.biology.common.exception.ApiException;
import com.biology.common.exception.error.ErrorCode.Business;
import com.biology.domain.manage.caiYangData.db.CaiYangDataEntity;
import com.biology.domain.manage.caiYangData.db.CaiYangDataService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CaiYangDataFactory {
    private final CaiYangDataService caiYangDataService;

    public CaiYangDataModel create() {
        return new CaiYangDataModel(caiYangDataService);
    }

    public CaiYangDataModel loadById(Long id) {
        CaiYangDataEntity byId = caiYangDataService.getById(id);
        if (byId == null) {
            throw new ApiException(Business.COMMON_OBJECT_NOT_FOUND, id, "接收");
        }
        return new CaiYangDataModel(byId, caiYangDataService);
    }
}
