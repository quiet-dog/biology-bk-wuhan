package com.biology.domain.manage.chuqin.model;

import org.springframework.stereotype.Component;

import com.biology.common.exception.ApiException;
import com.biology.common.exception.error.ErrorCode.Business;
import com.biology.domain.manage.chuqin.db.ChuQinEntity;
import com.biology.domain.manage.chuqin.db.ChuQinService;
import com.biology.domain.manage.personnel.db.PersonnelService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ChuQinFactory {

    private final ChuQinService chuQinService;

    private final PersonnelService personnelService;

    public ChuQinModel create() {
        return new ChuQinModel(chuQinService, personnelService);
    }

    public ChuQinModel loadById(Long chuQinId) {
        ChuQinEntity chuQinModel = chuQinService.getById(chuQinId);
        if (chuQinModel == null) {
            throw new ApiException(Business.COMMON_OBJECT_NOT_FOUND, chuQinId, "出勤记录");
        }
        return new ChuQinModel(chuQinModel, chuQinService, personnelService);
    }
}
