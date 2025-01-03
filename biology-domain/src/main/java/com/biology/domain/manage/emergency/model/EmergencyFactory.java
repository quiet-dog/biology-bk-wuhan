package com.biology.domain.manage.emergency.model;

import org.springframework.stereotype.Component;

import com.biology.common.exception.ApiException;
import com.biology.common.exception.error.ErrorCode.Business;
import com.biology.domain.manage.emergency.db.EmergencyEntity;
import com.biology.domain.manage.emergency.db.EmergencyEquipmentService;
import com.biology.domain.manage.emergency.db.EmergencyFileService;
import com.biology.domain.manage.emergency.db.EmergencyKeywordService;
import com.biology.domain.manage.emergency.db.KeyWordService;
import com.biology.domain.manage.emergency.db.EmergencyService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class EmergencyFactory {

    private final EmergencyService emergencyService;

    private final KeyWordService emergencyKeyWordService;

    private final EmergencyFileService emergencyFileService;

    private final EmergencyKeywordService emergencyKeywordService;

    private final EmergencyEquipmentService emergencyEquipmentService;

    public EmergencyModel create() {
        return new EmergencyModel(emergencyService, emergencyKeyWordService, emergencyFileService,
                emergencyKeywordService, emergencyEquipmentService);
    }

    public EmergencyModel loadById(Long emergencyId) {
        EmergencyEntity emergencyModel = emergencyService.getById(emergencyId);
        if (emergencyModel == null) {
            throw new ApiException(Business.COMMON_OBJECT_NOT_FOUND, emergencyId, "应急");
        }
        return new EmergencyModel(emergencyModel, emergencyService, emergencyKeyWordService, emergencyFileService,
                emergencyKeywordService, emergencyEquipmentService);
    }

}
