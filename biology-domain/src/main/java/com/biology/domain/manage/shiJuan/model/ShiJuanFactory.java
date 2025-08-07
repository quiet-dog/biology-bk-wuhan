package com.biology.domain.manage.shiJuan.model;

import org.springframework.stereotype.Component;

import com.biology.common.exception.ApiException;
import com.biology.common.exception.error.ErrorCode.Business;
import com.biology.domain.manage.shiJuan.db.ShiJuanEntity;
import com.biology.domain.manage.shiJuan.db.ShiJuanService;
import com.biology.domain.manage.shiJuan.model.ShiJuanModel;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ShiJuanFactory {
    private final ShiJuanService shiJuanService;

    public ShiJuanModel create() {
        return new ShiJuanModel(shiJuanService);
    }

    public ShiJuanModel loadById(Long id) {
        ShiJuanEntity byId = shiJuanService.getById(id);
        if (byId == null) {
            throw new ApiException(Business.COMMON_OBJECT_NOT_FOUND, id, "接收");
        }
        return new ShiJuanModel(byId, shiJuanService);
    }
}
