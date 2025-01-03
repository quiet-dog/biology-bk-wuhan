package com.biology.domain.manage.emergencyEvent.model;

import org.springframework.stereotype.Component;

import com.biology.common.exception.ApiException;
import com.biology.common.exception.error.ErrorCode.Business;
import com.biology.domain.manage.emergencyAlarm.db.EmergencyAlarmEntity;
import com.biology.domain.manage.emergencyAlarm.model.EmergencyAlarmModel;
import com.biology.domain.manage.emergencyEvent.db.EmergencyEventAlarmService;
import com.biology.domain.manage.emergencyEvent.db.EmergencyEventEntity;
import com.biology.domain.manage.emergencyEvent.db.EmergencyEventService;
import com.biology.domain.manage.emergencyEvent.db.EmergencyUserService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class EmergencyEventFactory {
    private final EmergencyEventService emergencyEventService;

    private final EmergencyUserService emergencyUserService;

    private final EmergencyEventAlarmService emergencyEventAlarmService;

    public EmergencyEventModel create() {
        return new EmergencyEventModel(emergencyEventService, emergencyUserService, emergencyEventAlarmService);
    }

    public EmergencyEventModel loadById(Long emergencyEventId) {
        EmergencyEventEntity emergencyEventEntity = emergencyEventService.getById(emergencyEventId);
        if (emergencyEventEntity == null) {
            throw new ApiException(Business.COMMON_OBJECT_NOT_FOUND, emergencyEventId, "报警事件");
        }
        return new EmergencyEventModel(emergencyEventEntity, emergencyEventService, emergencyUserService,
                emergencyEventAlarmService);
    }
}
