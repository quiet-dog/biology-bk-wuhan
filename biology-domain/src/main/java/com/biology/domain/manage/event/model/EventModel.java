package com.biology.domain.manage.event.model;

import org.springframework.beans.BeanUtils;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.biology.common.exception.ApiException;
import com.biology.common.exception.error.ErrorCode.Business;
import com.biology.domain.common.cache.CacheCenter;
import com.biology.domain.manage.equipment.db.EquipmentEntity;
import com.biology.domain.manage.equipment.db.EquipmentService;
import com.biology.domain.manage.event.command.AddEventCommand;
import com.biology.domain.manage.event.command.UpdateEventCommand;
import com.biology.domain.manage.event.db.EventEntity;
import com.biology.domain.manage.event.db.EventFileService;
import com.biology.domain.manage.event.db.EventService;
import com.biology.domain.manage.event.dto.EventDTO;
import com.biology.domain.manage.notification.command.AddNotificationCommand;
import com.biology.domain.manage.notification.model.NotificationFactory;
import com.biology.domain.manage.notification.model.NotificationModel;
import com.biology.domain.manage.threshold.db.ThresholdEmergencyService;
import com.biology.domain.manage.threshold.db.ThresholdEntity;
import com.biology.domain.manage.threshold.db.ThresholdService;
import com.biology.domain.manage.threshold.db.ThresholdSopEntity;
import com.biology.domain.manage.threshold.db.ThresholdSopService;
import com.biology.domain.manage.websocket.db.WebsocketService;
import com.biology.domain.manage.websocket.dto.ContentDTO;
import com.biology.domain.manage.websocket.dto.OnlineDTO;
import com.biology.domain.system.notice.model.NoticeModel;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class EventModel extends EventEntity {
    private EventFileService eventFileService;

    private EventService eventService;

    private EquipmentService equipmentService;

    private WebsocketService websocketService;

    private ThresholdSopService thresholdSopService;

    private ThresholdEmergencyService thresholdEmergencyService;

    private NotificationFactory notificationFactory;

    private ThresholdService thresholdService;

    public EventModel(EventFileService eventFileService, EventService eventService, EquipmentService equipmentService,
            WebsocketService websocketService, ThresholdSopService thresholdSopService,
            ThresholdEmergencyService thresholdEmergencyService, NotificationFactory notificationFactory) {
        this.eventFileService = eventFileService;
        this.eventService = eventService;
        this.equipmentService = equipmentService;
        this.websocketService = websocketService;
        this.thresholdSopService = thresholdSopService;
        this.thresholdEmergencyService = thresholdEmergencyService;
        this.notificationFactory = notificationFactory;
    }

    public EventModel(EventEntity entity, EventFileService eventFileService, EventService eventService,
            EquipmentService equipmentService, WebsocketService websocketService,
            ThresholdSopService thresholdSopService, ThresholdEmergencyService thresholdEmergencyService,
            NotificationFactory notificationFactory) {
        if (entity != null) {
            BeanUtils.copyProperties(entity, this);
        }
        this.eventFileService = eventFileService;
        this.eventService = eventService;
        this.equipmentService = equipmentService;
        this.websocketService = websocketService;
        this.thresholdSopService = thresholdSopService;
        this.thresholdEmergencyService = thresholdEmergencyService;
        this.notificationFactory = notificationFactory;
    }

    public void loadAddEventCommand(AddEventCommand command) {
        if (command != null) {
            BeanUtils.copyProperties(command, this, "eventId");
        }
    }

    public void loadUpdateEventCommand(UpdateEventCommand command) {
        if (command != null) {
            BeanUtils.copyProperties(command, this, "eventId");
        }
    }

    // 广播
    public void boardcast() {
        EventDTO eventDTO = new EventDTO(this);
        websocketService.sendTopicInfo(eventDTO);

        AddNotificationCommand command = new AddNotificationCommand();
        if (eventDTO.getLevel().equals("紧急") || eventDTO.getLevel().equals("重要")
                || eventDTO.getLevel().equals("中度")) {
            command.setNotificationType("通知");
        } else {
            command.setNotificationType("提醒");
        }
        command.setEventId(getEventId());
        command.setNotificationContent(eventDTO.getDescription());
        command.setNotificationTitle(eventDTO.getLevel());
        command.setImportance(2);
        NotificationModel noticeModel = notificationFactory.create();
        noticeModel.loadAddNotificationCommand(command);
        noticeModel.insert();

    }

    public void addCache() {
        OnlineDTO oDto = new OnlineDTO();
        String redisId = new String();
        if (getEnvironmentId() != null) {
            oDto.setEnvironmentId(getEnvironmentId());
            redisId = "environment-" + getEnvironmentId().toString();
            oDto.setIsOnline(true);
            oDto.setIsException(true);
            CacheCenter.onlineCache.set(redisId, oDto);
        }

        if (getThresholdId() != null) {
            oDto.setThresholdId(getThresholdId());
            redisId = "threshold-" + getThresholdId().toString();
            oDto.setIsOnline(true);
            oDto.setIsException(true);
            CacheCenter.onlineCache.set(redisId, oDto);
        }

        if (getEquipmentId() != null) {
            oDto.setEquipmentId(getEquipmentId());
            redisId = "equipment-" + getEquipmentId().toString();
            oDto.setIsOnline(true);
            oDto.setIsException(true);
            CacheCenter.onlineCache.set(redisId, oDto);
        }

    }

    public boolean insert() {
        super.insert();
        boardcast();
        return true;
    }

}
