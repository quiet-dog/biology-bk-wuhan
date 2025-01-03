package com.biology.domain.manage.detection.model;

import org.springframework.stereotype.Component;

import com.biology.common.exception.ApiException;
import com.biology.common.exception.error.ErrorCode.Business;
import com.biology.domain.manage.detection.db.DetectionEntity;
import com.biology.domain.manage.detection.db.DetectionService;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DetectionFactory {
    private final DetectionService detectionService;

    public DetectionModel create() {
        return new DetectionModel(detectionService);
    }

    public DetectionModel loadById(Long detectionId) {
        DetectionEntity detectionModel = detectionService.getById(detectionId);
        if (detectionModel == null) {
            throw new ApiException(Business.COMMON_OBJECT_NOT_FOUND, detectionId, "检测");
        }
        return new DetectionModel(detectionModel, detectionService);
    }
}
