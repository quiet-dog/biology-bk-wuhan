package com.biology.domain.manage.personnel.model;

import org.springframework.stereotype.Component;

import com.biology.common.exception.ApiException;
import com.biology.common.exception.error.ErrorCode.Business;
import com.biology.domain.manage.personnel.db.PersonnelCardService;
import com.biology.domain.manage.personnel.db.PersonnelCertificatesService;
import com.biology.domain.manage.personnel.db.PersonnelEntity;
import com.biology.domain.manage.personnel.db.PersonnelService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PersonnelFactory {

    private final PersonnelService personnelService;

    private final PersonnelCardService personnelCardService;

    private final PersonnelCertificatesService personnelCertificatesService;

    public PersonnelModel create() {
        return new PersonnelModel(personnelService, personnelCardService, personnelCertificatesService);
    }

    public PersonnelModel loadById(Long personnelId) {
        PersonnelEntity byId = personnelService.getById(personnelId);
        if (byId == null) {
            throw new ApiException(Business.COMMON_OBJECT_NOT_FOUND, personnelId, "人员");
        }
        return new PersonnelModel(byId, personnelService, personnelCardService, personnelCertificatesService);
    }
}
