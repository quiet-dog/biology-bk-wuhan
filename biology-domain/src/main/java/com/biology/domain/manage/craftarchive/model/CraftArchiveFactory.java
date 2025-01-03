package com.biology.domain.manage.craftarchive.model;

import org.springframework.stereotype.Component;
import com.biology.common.exception.ApiException;
import com.biology.common.exception.error.ErrorCode.Business;
import com.biology.domain.manage.craftarchive.db.CraftArchiveEntity;
import com.biology.domain.manage.craftarchive.db.CraftArchiveService;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CraftArchiveFactory {

    private final CraftArchiveService craftArchiveService;

    public CraftArchiveModel create() {
        return new CraftArchiveModel(craftArchiveService);
    }

    public CraftArchiveModel loadById(Long craftArchiveId) {
        CraftArchiveEntity entity = craftArchiveService.getById(craftArchiveId);
        if (entity == null) {
            throw new ApiException(Business.COMMON_OBJECT_NOT_FOUND, craftArchiveId, "工艺档案");
        }
        return new CraftArchiveModel(entity, craftArchiveService);
    }
}