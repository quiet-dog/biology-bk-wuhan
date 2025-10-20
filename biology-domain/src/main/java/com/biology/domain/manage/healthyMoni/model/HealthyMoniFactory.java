package com.biology.domain.manage.healthyMoni.model;

import org.springframework.stereotype.Component;

import com.biology.common.exception.ApiException;
import com.biology.common.exception.error.ErrorCode.Business;
import com.biology.domain.manage.healthyMoni.db.HealthyMoniEntity;
import com.biology.domain.manage.healthyMoni.db.HealthyMoniService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class HealthyMoniFactory {
    private final HealthyMoniService healthyMoniService;

    public HealthyMoniModel create() {
        return new HealthyMoniModel(this.healthyMoniService);
    }

    public HealthyMoniModel loadById(Long healthyMoniId) {
        HealthyMoniEntity healthyMoniModel = healthyMoniService.getById(healthyMoniId);
        if (healthyMoniModel == null) {
            throw new ApiException(Business.COMMON_OBJECT_NOT_FOUND, healthyMoniId, "健康");
        }
        return new HealthyMoniModel(healthyMoniModel, this.healthyMoniService);
    }

}
