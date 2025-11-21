package com.biology.domain.manage.event.model;

import java.util.Date;

import org.springframework.beans.BeanUtils;
import org.springframework.web.reactive.function.client.WebClient;

import com.alibaba.excel.util.DateUtils;
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

import cn.hutool.core.date.DateUtil;
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

    private WebClient opcClient;

    public EventModel(EventFileService eventFileService, EventService eventService, EquipmentService equipmentService,
            WebsocketService websocketService, ThresholdSopService thresholdSopService,
            ThresholdEmergencyService thresholdEmergencyService, NotificationFactory notificationFactory,
            WebClient opcClient) {
        this.eventFileService = eventFileService;
        this.eventService = eventService;
        this.equipmentService = equipmentService;
        this.websocketService = websocketService;
        this.thresholdSopService = thresholdSopService;
        this.thresholdEmergencyService = thresholdEmergencyService;
        this.notificationFactory = notificationFactory;
        this.opcClient = opcClient;
    }

    public EventModel(EventEntity entity, EventFileService eventFileService, EventService eventService,
            EquipmentService equipmentService, WebsocketService websocketService,
            ThresholdSopService thresholdSopService, ThresholdEmergencyService thresholdEmergencyService,
            NotificationFactory notificationFactory, WebClient opcClient) {
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
        this.opcClient = opcClient;
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
    public void boardcast(Boolean isNotSend) {
        EventDTO eventDTO = new EventDTO(this);

        if (!isNotSend) {
            websocketService.sendTopicInfo(eventDTO);
        }
        AddNotificationCommand command = new AddNotificationCommand();
        if (eventDTO.getLevel() == null) {
            return;
        }

        if (eventDTO.getLevel().equals("紧急") || eventDTO.getLevel().equals("重要")
                || eventDTO.getLevel().equals("中度")) {
            command.setNotificationType("通知");
        } else {
            command.setNotificationType("提醒");
            // command.setNotificationType("通知");
        }
        command.setEventId(getEventId());
        command.setNotificationContent(eventDTO.getDescription());
        command.setNotificationTitle(eventDTO.getLevel());
        command.setImportance(2);
        NotificationModel noticeModel = notificationFactory.create();
        noticeModel.loadAddNotificationCommand(command);
        noticeModel.insert();
        if (eventDTO.getType() != null && eventDTO.getType().equals("设备报警") || eventDTO.getType().equals("环境报警")) {
            opcClient.post()
                    .uri("/api/recSheBeiBaoJingApi")
                    .bodyValue(eventDTO)
                    .retrieve()
                    .bodyToMono(String.class)
                    .subscribe();
        }

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

        Boolean isNotSend = false;
        if (this.getEnvironmentId() != null && this.getEnvironmentId() > 0) {
            // 判断最近10分钟内是否存在相同的environmentId的事件
            QueryWrapper<EventEntity> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("environment_id", this.getEnvironmentId());
            queryWrapper.ge("create_time", DateUtil.offsetMinute(new Date(), -2));
            EventEntity eventEntity = new EventEntity().selectOne(queryWrapper);
            if (eventEntity != null) {
                isNotSend = true;
            }
        }

        if (this.getMaterialsId() != null && this.getMaterialsId() > 0) {
            // 判断最近10分钟内是否存在相同的materialsId的事件
            QueryWrapper<EventEntity> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("materials_id", this.getMaterialsId());
            queryWrapper.ge("create_time", DateUtil.offsetMinute(new Date(), -2));
            EventEntity eventEntity = new EventEntity().selectOne(queryWrapper);
            if (eventEntity != null) {
                isNotSend = true;
            }
        }

        if (this.getThresholdId() != null && this.getThresholdId() > 0) {
            // 判断最近10分钟内是否存在相同的thresholdId的事件
            QueryWrapper<EventEntity> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("threshold_id", this.getThresholdId());
            queryWrapper.ge("create_time", DateUtil.offsetMinute(new Date(), -10));
            EventEntity eventEntity = new EventEntity().selectOne(queryWrapper);
            System.out.println("websocketService.sendTopicInfo(eventDTO)222222==================");
            System.out.println("=========================aaa" + eventEntity);
            if (eventEntity != null) {
                System.out.println("thresholdId事件存在==================");
                System.out.println(eventEntity.getDescription());
                isNotSend = true;
            }
        }

        if (this.getCraftNodeId() != null && this.getCraftNodeId() > 0) {
            // 判断最近10分钟内是否存在相同的craftNodeId的事件
            QueryWrapper<EventEntity> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("craft_node_id", this.getCraftNodeId());
            queryWrapper.ge("create_time", DateUtil.offsetMinute(new Date(), -10));
            EventEntity eventEntity = new EventEntity().selectOne(queryWrapper);
            if (eventEntity != null) {
                isNotSend = true;
            }
        }

        System.out.println("websocketService.sendTopicInfo(eventDTO)==================");
        super.insert();
        boardcast(isNotSend);
        return true;
    }

}
