package com.biology.domain.manage.craftprocess.model;

import org.springframework.stereotype.Component;
import com.biology.common.exception.ApiException;
import com.biology.common.exception.error.ErrorCode.Business;
import com.biology.domain.manage.craftprocess.db.CraftProcessEntity;
import com.biology.domain.manage.craftprocess.db.CraftProcessService;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CraftProcessFactory {

    private final CraftProcessService craftProcessService;

    public CraftProcessModel create() {
        return new CraftProcessModel(craftProcessService);
    }

    public CraftProcessModel loadById(Long craftProcessId) {
        CraftProcessEntity entity = craftProcessService.getById(craftProcessId);
        if (entity == null) {
            throw new ApiException(Business.COMMON_OBJECT_NOT_FOUND, craftProcessId, "工艺流程图");
        }
        return new CraftProcessModel(entity, craftProcessService);
    }
}
