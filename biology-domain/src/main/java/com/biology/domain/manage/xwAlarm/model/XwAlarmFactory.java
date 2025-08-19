package com.biology.domain.manage.xwAlarm.model;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.biology.common.exception.ApiException;
import com.biology.common.exception.error.ErrorCode.Business;
import com.biology.domain.manage.xwAlarm.db.XwAlarmEntity;
import com.biology.domain.manage.xwAlarm.db.XwAlarmService;
import com.biology.domain.manage.xwAlarm.model.XwAlarmModel;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class XwAlarmFactory {
    private final XwAlarmService xwAlarmService;
    private final WebClient opcClient;

    public XwAlarmModel create() {
        return new XwAlarmModel(xwAlarmService, opcClient);
    }

    public XwAlarmModel loadById(Long id) {
        XwAlarmEntity byId = xwAlarmService.getById(id);
        if (byId == null) {
            throw new ApiException(Business.COMMON_OBJECT_NOT_FOUND, id, "接收");
        }
        return new XwAlarmModel(byId, xwAlarmService, opcClient);
    }
}
