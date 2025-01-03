package com.biology.domain.manage.event.model;

import org.springframework.stereotype.Component;

import com.biology.common.exception.ApiException;
import com.biology.common.exception.error.ErrorCode.Business;
import com.biology.domain.manage.equipment.db.EquipmentService;
import com.biology.domain.manage.event.db.EventEntity;
import com.biology.domain.manage.event.db.EventFileService;
import com.biology.domain.manage.event.db.EventService;
import com.biology.domain.manage.notification.model.NotificationFactory;
import com.biology.domain.manage.threshold.db.ThresholdEmergencyService;
import com.biology.domain.manage.threshold.db.ThresholdSopService;
import com.biology.domain.manage.websocket.db.WebsocketService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class EventFactory {

    private final EventService eventService;

    private final EventFileService eventFileService;

    private final EquipmentService equipmentService;

    private final WebsocketService websocketService;

    private final ThresholdSopService thresholdSopService;

    private final ThresholdEmergencyService thresholdEmergencyService;

    private final NotificationFactory notificationFactory;

    public EventModel create() {
        return new EventModel(eventFileService, eventService, equipmentService, websocketService, thresholdSopService,
                thresholdEmergencyService, notificationFactory);
    }

    public EventModel loadById(Long id) {
        EventEntity eventEntity = eventService.getById(id);
        if (eventEntity == null) {
            throw new ApiException(Business.COMMON_OBJECT_NOT_FOUND, id, "⌚");
        }
        return new EventModel(eventEntity, eventFileService, eventService, equipmentService, websocketService,
                thresholdSopService, thresholdEmergencyService, notificationFactory);
    }
}
