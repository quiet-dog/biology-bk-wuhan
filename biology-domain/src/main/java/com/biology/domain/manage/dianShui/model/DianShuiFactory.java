package com.biology.domain.manage.dianShui.model;

import org.springframework.stereotype.Component;

import com.biology.common.exception.ApiException;
import com.biology.common.exception.error.ErrorCode.Business;
import com.biology.domain.manage.dianShui.db.DianShuiEntity;
import com.biology.domain.manage.dianShui.db.DianShuiService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DianShuiFactory {
    private final DianShuiService dianShuiService;

    public DianShuiModel create() {
        return new DianShuiModel(dianShuiService);
    }

    public DianShuiModel loadById(Long dianShuiId) {
        DianShuiEntity dianShuiModel = dianShuiService.getById(dianShuiId);
        if (dianShuiModel == null) {
            throw new ApiException(Business.COMMON_OBJECT_NOT_FOUND, dianShuiId, "检测");
        }
        return new DianShuiModel(dianShuiModel, dianShuiService);
    }
}
