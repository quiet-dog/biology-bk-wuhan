package com.biology.domain.manage.threshold.model;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.biology.common.exception.ApiException;
import com.biology.common.exception.error.ErrorCode.Business;
import com.biology.domain.manage.threshold.db.ThresholdEmergencyService;
import com.biology.domain.manage.threshold.db.ThresholdEntity;
import com.biology.domain.manage.threshold.db.ThresholdService;
import com.biology.domain.manage.threshold.db.ThresholdSopService;
import com.biology.domain.manage.threshold.db.ThresholdValueService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ThresholdFactory {

    private final ThresholdService thresholdService;

    private final ThresholdValueService thresholdValueService;

    private final ThresholdEmergencyService thresholdEmergencyService;

    private final ThresholdSopService thresholdSopService;

    private final WebClient opcClient;

    public ThresholdModel create() {
        return new ThresholdModel(thresholdService, thresholdValueService, thresholdEmergencyService,
                thresholdSopService, opcClient);
    }

    public ThresholdModel loadById(Long thresholdId) {
        ThresholdEntity thresholdModel = thresholdService.getById(thresholdId);
        if (thresholdModel == null) {
            throw new ApiException(Business.COMMON_OBJECT_NOT_FOUND, thresholdId, "Threshold");
        }
        return new ThresholdModel(thresholdModel, thresholdService, thresholdValueService, thresholdEmergencyService,
                thresholdSopService, opcClient);
    }
}
