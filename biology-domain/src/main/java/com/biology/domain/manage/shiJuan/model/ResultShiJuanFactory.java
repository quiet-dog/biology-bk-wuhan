package com.biology.domain.manage.shiJuan.model;

import org.springframework.stereotype.Component;

import com.biology.common.exception.ApiException;
import com.biology.common.exception.error.ErrorCode.Business;
import com.biology.domain.manage.shiJuan.db.ResultShiJuanEntity;
import com.biology.domain.manage.shiJuan.db.ResultShiJuanService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ResultShiJuanFactory {
    private final ResultShiJuanService resultShiJuanService;

    public ResultShiJuanModel create() {
        return new ResultShiJuanModel(resultShiJuanService);
    }

    public ResultShiJuanModel loadById(Long id) {
        ResultShiJuanEntity byId = resultShiJuanService.getById(id);
        if (byId == null) {
            throw new ApiException(Business.COMMON_OBJECT_NOT_FOUND, id, "接收");
        }
        return new ResultShiJuanModel(byId, resultShiJuanService);
    }
}
