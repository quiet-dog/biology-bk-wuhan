package com.biology.domain.manage.craftdisposemanual.model;

import org.springframework.stereotype.Component;
import com.biology.common.exception.ApiException;
import com.biology.common.exception.error.ErrorCode.Business;
import com.biology.domain.manage.craftdisposemanual.db.DisposeManualEntity;
import com.biology.domain.manage.craftdisposemanual.db.DisposeManualService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DisposeManualFactory {

    private final DisposeManualService disposeManualService;

    public DisposeManualModel create() {
        return new DisposeManualModel(disposeManualService);
    }

    public DisposeManualModel loadById(Long disposeManualId) {
        DisposeManualEntity entity = disposeManualService.getById(disposeManualId);
        if (entity == null) {
            throw new ApiException(Business.COMMON_OBJECT_NOT_FOUND, disposeManualId, "处置手册");
        }
        return new DisposeManualModel(entity, disposeManualService);
    }
}
