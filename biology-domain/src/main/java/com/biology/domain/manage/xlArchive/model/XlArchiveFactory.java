package com.biology.domain.manage.xlArchive.model;

import org.springframework.stereotype.Component;

import com.biology.common.exception.ApiException;
import com.biology.common.exception.error.ErrorCode.Business;
import com.biology.domain.manage.personnel.model.PersonnelFactory;
import com.biology.domain.manage.shiJuan.model.ResultShiJuanFactory;
import com.biology.domain.manage.xlArchive.db.XlArchiveEntity;
import com.biology.domain.manage.xlArchive.db.XlArchiveService;
import com.biology.domain.manage.xlArchive.model.XlArchiveModel;
import com.biology.domain.system.user.model.UserModelFactory;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class XlArchiveFactory {
    private final XlArchiveService xlArchiveService;

    private final UserModelFactory userModelFactory;

    private final PersonnelFactory personnelFactory;

    public XlArchiveModel create() {
        return new XlArchiveModel(xlArchiveService, userModelFactory, personnelFactory);
    }

    public XlArchiveModel loadById(Long id) {
        XlArchiveEntity byId = xlArchiveService.getById(id);
        if (byId == null) {
            throw new ApiException(Business.COMMON_OBJECT_NOT_FOUND, id, "接收");
        }
        return new XlArchiveModel(byId, xlArchiveService, userModelFactory, personnelFactory);
    }
}
