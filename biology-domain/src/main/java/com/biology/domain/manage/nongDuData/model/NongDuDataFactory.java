package com.biology.domain.manage.nongDuData.model;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.biology.common.exception.ApiException;
import com.biology.common.exception.error.ErrorCode.Business;
import com.biology.domain.manage.nongDuData.db.NongDuDataEntity;
import com.biology.domain.manage.nongDuData.db.NongDuDataService;
import com.biology.domain.manage.nongDuData.model.NongDuDataModel;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class NongDuDataFactory {
    private final NongDuDataService nongDuDataService;

    private final WebClient opcClient;

    public NongDuDataModel create() {
        return new NongDuDataModel(nongDuDataService, opcClient);
    }

    public NongDuDataModel loadById(Long id) {
        NongDuDataEntity byId = nongDuDataService.getById(id);
        if (byId == null) {
            throw new ApiException(Business.COMMON_OBJECT_NOT_FOUND, id, "接收");
        }
        return new NongDuDataModel(byId, nongDuDataService, opcClient);
    }
}
