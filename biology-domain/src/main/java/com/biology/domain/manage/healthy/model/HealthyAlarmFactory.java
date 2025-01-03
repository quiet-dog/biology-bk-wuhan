package com.biology.domain.manage.healthy.model;

import org.springframework.stereotype.Component;

import com.biology.common.exception.ApiException;
import com.biology.common.exception.error.ErrorCode.Business;
import com.biology.domain.manage.healthy.db.HealthyAlarmEntity;
import com.biology.domain.manage.healthy.db.HealthyAlarmService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class HealthyAlarmFactory {

    private final HealthyAlarmService healthyAlarmService;

    public HealthyAlarmModel create() {
        return new HealthyAlarmModel(this.healthyAlarmService);
    }

    public HealthyAlarmModel loadById(Long healthyAlarmId) {
        HealthyAlarmEntity healthyAlarmModel = healthyAlarmService.getById(healthyAlarmId);
        if (healthyAlarmModel == null) {
            throw new ApiException(Business.COMMON_OBJECT_NOT_FOUND, healthyAlarmId, "健康报警");
        }
        return new HealthyAlarmModel(healthyAlarmModel, this.healthyAlarmService);
    }
}
