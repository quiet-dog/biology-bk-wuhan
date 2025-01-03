package com.biology.domain.manage.environment.model;

import org.springframework.stereotype.Component;

import com.biology.common.exception.ApiException;
import com.biology.common.exception.error.ErrorCode.Business;
import com.biology.domain.manage.alarmlevel.db.AlarmlevelDetailService;
import com.biology.domain.manage.environment.db.EnvironmentEmergencyService;
import com.biology.domain.manage.environment.db.EnvironmentEntity;
import com.biology.domain.manage.environment.db.EnvironmentService;
import com.biology.domain.manage.environment.db.EnvironmentSopService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class EnvironmentFactory {
    private final EnvironmentService environmentService;

    private final AlarmlevelDetailService alarmlevelDetailService;

    private final EnvironmentSopService environmentSopService;

    private final EnvironmentEmergencyService environmentEmergencyService;

    public EnvironmentModel create() {
        return new EnvironmentModel(environmentService, alarmlevelDetailService, environmentSopService,
                environmentEmergencyService);
    }

    public EnvironmentModel loadById(Long environmentId) {
        EnvironmentEntity environmentModel = environmentService.getById(environmentId);
        if (environmentModel == null) {
            throw new ApiException(Business.COMMON_OBJECT_NOT_FOUND, environmentId, "环境");
        }
        return new EnvironmentModel(environmentModel, environmentService, alarmlevelDetailService,
                environmentSopService,
                environmentEmergencyService);
    }

}
