package com.biology.domain.manage.smAlarm.model;

import org.springframework.stereotype.Component;

import com.biology.common.exception.ApiException;
import com.biology.common.exception.error.ErrorCode.Business;
import com.biology.domain.manage.smAlarm.db.SmAlarmEntity;
import com.biology.domain.manage.smAlarm.db.SmAlarmService;
import com.biology.domain.manage.smAlarm.model.SmAlarmModel;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SmAlarmFactory {
    private final SmAlarmService smAlarmService;

    public SmAlarmModel create() {
        return new SmAlarmModel(smAlarmService);
    }

    public SmAlarmModel loadById(Long id) {
        SmAlarmEntity byId = smAlarmService.getById(id);
        if (byId == null) {
            throw new ApiException(Business.COMMON_OBJECT_NOT_FOUND, id, "接收");
        }
        return new SmAlarmModel(byId, smAlarmService);
    }
}
