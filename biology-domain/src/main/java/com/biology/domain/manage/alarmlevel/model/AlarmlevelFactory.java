package com.biology.domain.manage.alarmlevel.model;

import org.springframework.stereotype.Component;

import com.biology.common.exception.ApiException;
import com.biology.common.exception.error.ErrorCode.Business;
import com.biology.domain.manage.alarmlevel.db.AlarmlevelDetailService;
import com.biology.domain.manage.alarmlevel.db.AlarmlevelEntity;
import com.biology.domain.manage.alarmlevel.db.AlarmlevelService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AlarmlevelFactory {

    private final AlarmlevelService alarmlevelService;

    private final AlarmlevelDetailService alarmlevelDetailService;

    public AlarmlevelModel create() {
        return new AlarmlevelModel(alarmlevelService, alarmlevelDetailService);
    }

    public AlarmlevelModel loadById(Long alarmlevelId) {

        AlarmlevelEntity alarmlevelModel = alarmlevelService.getById(alarmlevelId);

        if (alarmlevelModel == null) {
            throw new ApiException(Business.COMMON_OBJECT_NOT_FOUND, alarmlevelId, "Alarmlevel");
        }

        return new AlarmlevelModel(alarmlevelModel, alarmlevelService, alarmlevelDetailService);
    }
}
