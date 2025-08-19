package com.biology.admin.controller.manage;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.cache.RedisCache;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.biology.common.core.dto.ResponseDTO;
import com.biology.domain.common.cache.CacheCenter;
import com.biology.domain.manage.alarmlevel.db.AlarmlevelDetailEntity;
import com.biology.domain.manage.craftnode.db.CraftNodeEntity;
import com.biology.domain.manage.craftnode.db.CraftNodeEquipmentEntity;
import com.biology.domain.manage.craftnode.db.CraftNodeService;
import com.biology.domain.manage.detection.DetectionApplicationService;
import com.biology.domain.manage.detection.command.AddDetectionCommand;
import com.biology.domain.manage.detection.model.DetectionModel;
import com.biology.domain.manage.emergencyAlarm.EmergencyAlarmApplicationService;
import com.biology.domain.manage.emergencyAlarm.command.AddEmergencyAlarmCommand;
import com.biology.domain.manage.environment.db.EnvironmentEmergencyService;
import com.biology.domain.manage.environment.db.EnvironmentEntity;
import com.biology.domain.manage.environment.db.EnvironmentSopService;
import com.biology.domain.manage.equipment.EquipmentApplicationService;
import com.biology.domain.manage.equipment.EquipmentDataApplicationService;
import com.biology.domain.manage.equipment.command.AddEquipmentCommand;
import com.biology.domain.manage.equipment.command.AddEquipmentDataCommand;
import com.biology.domain.manage.equipment.db.EquipmentEntity;
import com.biology.domain.manage.equipment.db.EquipmentService;
import com.biology.domain.manage.equipment.model.EquipmentDataModel;
import com.biology.domain.manage.event.EventApplicationService;
import com.biology.domain.manage.event.command.AddEventCommand;
import com.biology.domain.manage.healthy.command.AddHealthyCommand;
import com.biology.domain.manage.healthy.model.HealthyFactory;
import com.biology.domain.manage.healthy.model.HealthyModel;
import com.biology.domain.manage.moni.MoniApplicationService;
import com.biology.domain.manage.notification.command.AddNotificationCommand;
import com.biology.domain.manage.personnel.dto.PersonnelDTO;
import com.biology.domain.manage.threshold.ThresholdApplicationService;
import com.biology.domain.manage.threshold.db.ThresholdEmergencyService;
import com.biology.domain.manage.threshold.db.ThresholdEntity;
import com.biology.domain.manage.threshold.db.ThresholdSopEntity;
import com.biology.domain.manage.threshold.db.ThresholdSopService;
import com.biology.domain.manage.threshold.db.ThresholdValueEntity;
import com.biology.domain.manage.websocket.db.WebsocketService;
import com.biology.domain.manage.websocket.dto.ContentDTO;
import com.biology.domain.manage.websocket.dto.DeviceDTO;
import com.biology.domain.manage.websocket.dto.HealthyWsDTO;
import com.biology.domain.manage.websocket.dto.OnlineDTO;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class WebsocketController {

    // private final EventApplicationService eventApplicationService;

    // private final ThresholdApplicationService thresholdApplicationService;

    // private final EquipmentApplicationService equipmentApplicationService;

    // private final WebsocketService websocketService;

    // private final DetectionApplicationService detectionApplicationService;

    // private final EquipmentDataApplicationService
    // equipmentDataApplicationService;

    // private final EmergencyAlarmApplicationService
    // emergencyAlarmApplicationService;

    // private final ThresholdSopService thresholdSopService;

    // private final ThresholdEmergencyService thresholdEmergencyService;

    // private final EnvironmentSopService environmentSopService;

    // private final EnvironmentEmergencyService environmentEmergencyService;

    // private final CraftNodeService craftNodeService;

    private final HealthyFactory healthyFactory;

    private final MoniApplicationService moniApplicationService;

    @MessageMapping("/deviceInfo")
    // @SendTo("/topic/data")
    public void sendMsg(DeviceDTO deviceDTO) {
        moniApplicationService.sendMsg(deviceDTO);

        return;
    }

    public String checkEnvironmentDescription(EnvironmentEntity environmentEntity,
            double value) {
        return String.format("位号为%s的%s-%s数值为%.2f,触发报警", environmentEntity.getTag(),
                environmentEntity.getMonitoringPoint(), environmentEntity.getUnitName(), value);
    }

    public String checkEmergencyAlarmDescription(EquipmentEntity equipmentEntity, ThresholdEntity thresholdEntity,
            double value) {
        return String.format("设备编号为%s的%s-%s为%.2f,触发报警", equipmentEntity.getEquipmentCode(),
                equipmentEntity.getEquipmentName(), thresholdEntity.getEquipmentIndex(), value);
    }

    @MessageMapping("/healthy")
    public void sendPersonnelMsg(HealthyWsDTO hDto) {
        if (hDto.getPersonnelId() != null) {
            AddHealthyCommand aCommand = new AddHealthyCommand();
            aCommand.setPersonnelId(hDto.getPersonnelId());
            aCommand.setHeartRate(hDto.getHeartRate());
            aCommand.setHighBloodPressure(hDto.getHighBloodPressure());
            aCommand.setLowBloodPressure(hDto.getLowBloodPressure());
            aCommand.setTemperature(hDto.getTemperature());
            HealthyModel hModel = healthyFactory.create();
            hModel.loadAddHealthyCommand(aCommand);
            hModel.insert();
        }
    }
}
