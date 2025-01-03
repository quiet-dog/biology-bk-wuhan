package com.biology.domain.manage.receive.model;

import org.springframework.stereotype.Component;

import com.biology.common.exception.ApiException;
import com.biology.common.exception.error.ErrorCode.Business;
import com.biology.domain.manage.alarm.AlarmApplicationService;
import com.biology.domain.manage.alarm.db.AlarmService;
import com.biology.domain.manage.alarm.model.AlarmFactory;
import com.biology.domain.manage.materials.db.MaterialsService;
import com.biology.domain.manage.materials.db.MaterialsValueService;
import com.biology.domain.manage.receive.db.ReceiveEntity;
import com.biology.domain.manage.receive.db.ReceiveService;
import com.biology.domain.manage.websocket.db.WebsocketService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ReceiveFactory {

    private final ReceiveService receiveService;
    private final WebsocketService websocketService;
    private final MaterialsService materialsService;
    private final MaterialsValueService materialsValueService;
    private final AlarmService alarmService;
    private final AlarmFactory alarmFactory;
    private final AlarmApplicationService alarmApplicationService;

    public ReceiveModel create() {
        return new ReceiveModel(receiveService, websocketService, materialsService, materialsValueService,
                alarmService, alarmApplicationService);
    }

    public ReceiveModel loadById(Long receiveId) {
        ReceiveEntity byId = receiveService.getById(receiveId);
        if (byId == null) {
            throw new ApiException(Business.COMMON_OBJECT_NOT_FOUND, receiveId, "接收");
        }
        return new ReceiveModel(byId, receiveService, websocketService, materialsService, materialsValueService,
                alarmService, alarmApplicationService);
    }
}
