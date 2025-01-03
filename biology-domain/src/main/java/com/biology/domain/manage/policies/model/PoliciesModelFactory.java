package com.biology.domain.manage.policies.model;

import org.springframework.stereotype.Component;

import com.biology.common.exception.ApiException;
import com.biology.common.exception.error.ErrorCode.Business;
import com.biology.domain.manage.policies.db.PoliciesAppendixService;
import com.biology.domain.manage.policies.db.PoliciesEntity;
import com.biology.domain.manage.policies.db.PoliciesService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PoliciesModelFactory {
    private final PoliciesService policiesService;

    private final PoliciesAppendixService policiesAppendixService;

    public PoliciesModel create() {
        return new PoliciesModel(policiesService, policiesAppendixService);
    }

    public PoliciesModel loadById(Long policiesId) {
        PoliciesEntity byId = policiesService.getById(policiesId);
        if (byId == null) {
            throw new ApiException(Business.COMMON_OBJECT_NOT_FOUND, policiesId, "政策");
        }
        PoliciesModel policiesModel = new PoliciesModel(byId, policiesService, policiesAppendixService);
        return policiesModel;
    }
}
