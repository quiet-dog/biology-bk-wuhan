package com.biology.domain.manage.xunJianHistory.model;

import org.springframework.stereotype.Component;

import com.biology.common.exception.ApiException;
import com.biology.common.exception.error.ErrorCode.Business;
import com.biology.domain.manage.xunJianHistory.db.XunJianHistoryEntity;
import com.biology.domain.manage.xunJianHistory.db.XunJianHistoryService;
import com.biology.domain.manage.xunJianHistory.model.XunJianHistoryModel;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class XunJianHistoryFactory {
    private final XunJianHistoryService xunJianHistoryService;

    public XunJianHistoryModel create() {
        return new XunJianHistoryModel(xunJianHistoryService);
    }

    public XunJianHistoryModel loadById(Long id) {
        XunJianHistoryEntity byId = xunJianHistoryService.getById(id);
        if (byId == null) {
            throw new ApiException(Business.COMMON_OBJECT_NOT_FOUND, id, "巡检");
        }
        return new XunJianHistoryModel(byId, xunJianHistoryService);
    }
}
