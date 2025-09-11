package com.biology.domain.manage.xunJian.model;

import org.springframework.stereotype.Component;

import com.biology.common.exception.ApiException;
import com.biology.common.exception.error.ErrorCode.Business;
import com.biology.domain.manage.xunJian.db.XunJianEntity;
import com.biology.domain.manage.xunJian.db.XunJianService;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class XunJianFactory {

    private final XunJianService xunJianService;

    public XunJianModel create() {
        return new XunJianModel(xunJianService);
    }

    public XunJianModel loadById(Long id) {
        XunJianEntity byId = xunJianService.getById(id);
        if (byId == null) {
            throw new ApiException(Business.COMMON_OBJECT_NOT_FOUND, id, "巡检");
        }
        return new XunJianModel(byId, xunJianService);
    }
}
