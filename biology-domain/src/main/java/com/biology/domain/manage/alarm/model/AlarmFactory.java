package com.biology.domain.manage.alarm.model;

import org.springframework.stereotype.Component;

import com.biology.common.exception.ApiException;
import com.biology.common.exception.error.ErrorCode.Business;
import com.biology.domain.manage.alarm.db.AlarmEntity;
import com.biology.domain.manage.alarm.db.AlarmService;
import com.biology.domain.manage.event.model.EventFactory;
import com.biology.domain.manage.materials.db.MaterialsService;
import com.biology.domain.manage.materials.db.MaterialsValueService;
import com.biology.domain.manage.receive.db.ReceiveService;
import com.biology.domain.manage.websocket.db.WebsocketService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AlarmFactory {

    private final AlarmService alarmService;

    private final WebsocketService websocketService;

    private final MaterialsValueService materialsValueService;

    private final ReceiveService receiveService;

    private final MaterialsService materialsService;

    private final EventFactory eventFactory;

    public AlarmModel create() {
        return new AlarmModel(alarmService, websocketService, materialsValueService, receiveService, materialsService,
                eventFactory);
    }

    public AlarmModel loadById(Long id) {
        AlarmEntity alarmEntity = alarmService.getById(id);
        if (alarmEntity == null) {
            throw new ApiException(Business.COMMON_OBJECT_NOT_FOUND, id, "Alarm");
        }

        return new AlarmModel(alarmEntity, alarmService, websocketService, materialsValueService, receiveService,
                materialsService, eventFactory);
    }

}
