package com.biology.domain.manage.healthy.model;

import org.springframework.stereotype.Component;

import com.biology.common.exception.ApiException;
import com.biology.common.exception.error.ErrorCode.Business;
import com.biology.domain.manage.healthy.db.HealthyAlarmService;
import com.biology.domain.manage.healthy.db.HealthyEntity;
import com.biology.domain.manage.healthy.db.HealthyService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class HealthyFactory {

    private final HealthyService healthyService;

    private final HealthyAlarmService healthyAlarmService;

    public HealthyModel create() {
        return new HealthyModel(this.healthyService, this.healthyAlarmService);
    }

    public HealthyModel loadById(Long healthyId) {
        HealthyEntity healthyModel = healthyService.getById(healthyId);
        if (healthyModel == null) {
            throw new ApiException(Business.COMMON_OBJECT_NOT_FOUND, healthyId, "健康");
        }
        return new HealthyModel(healthyModel, this.healthyService, this.healthyAlarmService);
    }

}
