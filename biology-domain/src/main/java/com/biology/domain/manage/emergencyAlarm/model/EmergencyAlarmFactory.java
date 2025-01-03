package com.biology.domain.manage.emergencyAlarm.model;

import org.springframework.stereotype.Component;

import com.biology.common.exception.ApiException;
import com.biology.common.exception.error.ErrorCode.Business;
import com.biology.domain.manage.emergencyAlarm.db.EmergencyAlarmEntity;
import com.biology.domain.manage.emergencyAlarm.db.EmergencyAlarmService;
import com.biology.domain.manage.emergencyAlarm.db.EmergencyConnectService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class EmergencyAlarmFactory {
    private final EmergencyAlarmService emergencyAlarmService;

    private final EmergencyConnectService emergencyConnectService;

    public EmergencyAlarmModel create() {
        return new EmergencyAlarmModel(emergencyAlarmService, emergencyConnectService);
    }

    public EmergencyAlarmModel loadById(Long emergencyAlarmId) {
        EmergencyAlarmEntity emergencyAlarmModel = emergencyAlarmService.getById(emergencyAlarmId);
        if (emergencyAlarmModel == null) {
            throw new ApiException(Business.COMMON_OBJECT_NOT_FOUND, emergencyAlarmId, "应急告警");
        }
        return new EmergencyAlarmModel(emergencyAlarmModel, emergencyAlarmService, emergencyConnectService);
    }

}
