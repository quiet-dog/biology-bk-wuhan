package com.biology.domain.manage.smData.model;

import org.springframework.stereotype.Component;

import com.biology.common.exception.ApiException;
import com.biology.common.exception.error.ErrorCode.Business;
import com.biology.domain.manage.smData.db.SmDataEntity;
import com.biology.domain.manage.smData.db.SmDataService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SmDataFactory {
    private final SmDataService smDataService;

    public SmDataModel create() {
        return new SmDataModel(smDataService);
    }

    public SmDataModel loadById(Long id) {
        SmDataEntity byId = smDataService.getById(id);
        if (byId == null) {
            throw new ApiException(Business.COMMON_OBJECT_NOT_FOUND, id, "接收");
        }
        return new SmDataModel(byId, smDataService);
    }
}
