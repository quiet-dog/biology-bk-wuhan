package com.biology.domain.manage.xlFangAn.model;

import org.springframework.stereotype.Component;

import com.biology.common.exception.ApiException;
import com.biology.common.exception.error.ErrorCode.Business;
import com.biology.domain.manage.personnel.db.PersonnelService;
import com.biology.domain.manage.shiJuan.model.ResultShiJuanFactory;
import com.biology.domain.manage.xlFangAn.db.XlFangAnEntity;
import com.biology.domain.manage.xlFangAn.db.XlFangAnService;
import com.biology.domain.manage.xlFangAn.model.XlFangAnModel;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class XlFangAnFactory {
    private final XlFangAnService xlFangAnService;

    private final ResultShiJuanFactory resultShiJuanFactory;

    public XlFangAnModel create() {
        return new XlFangAnModel(xlFangAnService, resultShiJuanFactory);
    }

    public XlFangAnModel loadById(Long id) {
        XlFangAnEntity byId = xlFangAnService.getById(id);
        if (byId == null) {
            throw new ApiException(Business.COMMON_OBJECT_NOT_FOUND, id, "接收");
        }
        return new XlFangAnModel(byId, xlFangAnService, resultShiJuanFactory);
    }

}
