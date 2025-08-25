package com.biology.domain.manage.xsData.model;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.biology.common.exception.ApiException;
import com.biology.common.exception.error.ErrorCode.Business;
import com.biology.domain.manage.shiJuan.model.ResultShiJuanFactory;
import com.biology.domain.manage.xsData.db.XsDataEntity;
import com.biology.domain.manage.xsData.db.XsDataService;
import com.biology.domain.manage.xsData.model.XsDataModel;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class XsDataFactory {
    private final XsDataService xsDataService;
    private final WebClient opcClient;

    public XsDataModel create() {
        return new XsDataModel(xsDataService, opcClient);
    }

    public XsDataModel loadById(Long id) {
        XsDataEntity byId = xsDataService.getById(id);
        if (byId == null) {
            throw new ApiException(Business.COMMON_OBJECT_NOT_FOUND, id, "接收");
        }
        return new XsDataModel(byId, xsDataService, opcClient);
    }

}
