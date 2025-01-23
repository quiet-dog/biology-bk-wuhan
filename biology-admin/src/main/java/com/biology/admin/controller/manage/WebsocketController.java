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

    private final EventApplicationService eventApplicationService;

    private final ThresholdApplicationService thresholdApplicationService;

    private final EquipmentApplicationService equipmentApplicationService;

    private final WebsocketService websocketService;

    private final DetectionApplicationService detectionApplicationService;

    private final EquipmentDataApplicationService equipmentDataApplicationService;

    private final EmergencyAlarmApplicationService emergencyAlarmApplicationService;

    private final ThresholdSopService thresholdSopService;

    private final ThresholdEmergencyService thresholdEmergencyService;

    private final EnvironmentSopService environmentSopService;

    private final EnvironmentEmergencyService environmentEmergencyService;

    private final CraftNodeService craftNodeService;

    private final HealthyFactory healthyFactory;

    @MessageMapping("/deviceInfo")
    // @SendTo("/topic/data")
    public void sendMsg(DeviceDTO deviceDTO) {

        websocketService.sendTopicData(deviceDTO);

        AddEventCommand command = new AddEventCommand();
        OnlineDTO oDto = new OnlineDTO();
        oDto.setIsOnline(true);
        oDto.setIsException(false);
        String redisId = new String();
        if (deviceDTO.getDeviceType().equals("环境档案")) {
            // 存储在缓存中
            {
                if (deviceDTO.getEnvironmentAlarmInfo().getEnvironmentId() != null) {
                    redisId = "environment-" + deviceDTO.getEnvironmentAlarmInfo().getEnvironmentId();
                    oDto.setEnvironmentData(deviceDTO.getEnvironmentAlarmInfo().getValue());
                    oDto.setEnvironmentId(deviceDTO.getEnvironmentAlarmInfo().getEnvironmentId());
                    CacheCenter.onlineCache.set(redisId, oDto);
                }
            }

            QueryWrapper<EnvironmentEntity> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("environment_id", deviceDTO.getEnvironmentAlarmInfo().getEnvironmentId());
            EnvironmentEntity environmentEntity = new EnvironmentEntity().selectOne(queryWrapper);
            if (environmentEntity == null) {
                return;
            }
            // 添加数据
            AddDetectionCommand addDetectionCommand = new AddDetectionCommand();
            addDetectionCommand.setEnvironmentId(deviceDTO.getEnvironmentAlarmInfo().getEnvironmentId());
            addDetectionCommand.setValue(deviceDTO.getEnvironmentAlarmInfo().getValue());
            addDetectionCommand.setPower(deviceDTO.getEnvironmentAlarmInfo().getPower());
            addDetectionCommand.setWaterValue(deviceDTO.getEnvironmentAlarmInfo().getWaterValue());
            addDetectionCommand.setElectricityValue(deviceDTO.getEnvironmentAlarmInfo().getElectricityValue());
            DetectionModel detectionModel = detectionApplicationService.addDetection(addDetectionCommand);

            // 检查是否报警
            AlarmlevelDetailEntity alarmlevelDetailEntity = eventApplicationService
                    .checkEnvironment(deviceDTO.getEnvironmentAlarmInfo());
            if (alarmlevelDetailEntity == null) {
                return;
            }

            // 添加应急调度下的报警信息
            {
                AddEmergencyAlarmCommand addEmergencyAlarmCommand = new AddEmergencyAlarmCommand();
                addEmergencyAlarmCommand.setLevel(alarmlevelDetailEntity.getLevel());
                addEmergencyAlarmCommand.setDetectionId(detectionModel.getDetectionId());
                addEmergencyAlarmCommand.setDescription(checkEnvironmentDescription(environmentEntity,
                        deviceDTO.getEnvironmentAlarmInfo().getValue()));
                addEmergencyAlarmCommand.setCode("");
                addEmergencyAlarmCommand.setType("环境报警");
                addEmergencyAlarmCommand.setSopIds(
                        environmentSopService
                                .getSopIdsByEnvironmentId(deviceDTO.getEnvironmentAlarmInfo().getEnvironmentId()));
                addEmergencyAlarmCommand.setEmergencyIds(environmentEmergencyService
                        .getEmergencyIdsByEnvironmentId(deviceDTO.getEnvironmentAlarmInfo().getEnvironmentId()));
                if (addEmergencyAlarmCommand.getSopIds() != null
                        || addEmergencyAlarmCommand.getEmergencyIds() != null) {
                    emergencyAlarmApplicationService.addEmergencyAlarm(addEmergencyAlarmCommand);
                }
            }

            // 添加报警平台下的报警信息
            {
                command.setEnvironmentValue(deviceDTO.getEnvironmentAlarmInfo().getValue());
                command.setType("环境报警");
                command.setLevel(alarmlevelDetailEntity.getLevel());
                command.setEnvironmentId(deviceDTO.getEnvironmentAlarmInfo().getEnvironmentId());
                command.setEnvironmentUnit(deviceDTO.getEnvironmentAlarmInfo().getUnit());
                command.setAlarmlevelId(alarmlevelDetailEntity.getAlarmlevelId());
                eventApplicationService.createEvent(command);
            }
        }

        if (deviceDTO.getDeviceType().equals("设备档案")) {
            QueryWrapper<ThresholdEntity> queryWrapper1 = new QueryWrapper<>();
            queryWrapper1.eq("threshold_id",
                    deviceDTO.getEquipmentInfo().getThresholdId());
            ThresholdEntity thresholdEntity = new ThresholdEntity().selectOne(queryWrapper1);
            deviceDTO.getEquipmentInfo().setEquipmentId(thresholdEntity.getEquipmentId());

            if (deviceDTO.getEquipmentInfo().getEquipmentId() != null) {
                redisId = "equipment-" + deviceDTO.getEquipmentInfo().getEquipmentId();
                oDto.setEquipmentId(deviceDTO.getEquipmentInfo().getEquipmentId());
                CacheCenter.onlineCache.set(redisId, oDto);
            }

            if (deviceDTO.getEquipmentInfo().getThresholdId() != null) {
                System.out.printf(
                        "进入到设备档案获取阈值设置当中========================================================= \n");
                redisId = "threshold-" + deviceDTO.getEquipmentInfo().getThresholdId();
                oDto.setThresholdData(deviceDTO.getEquipmentInfo().getValue());
                oDto.setThresholdId(deviceDTO.getEquipmentInfo().getThresholdId());
                CacheCenter.onlineCache.set(redisId, oDto);
            }

            QueryWrapper<EquipmentEntity> queryWrapper2 = new QueryWrapper<>();
            queryWrapper2.eq("equipment_id", deviceDTO.getEquipmentInfo().getEquipmentId());
            EquipmentEntity equipmentEntity = new EquipmentEntity().selectOne(queryWrapper2);

            System.out.printf(
                    "找到设备档案的阈值设置========================================================= \n");
            // 添加数据
            AddEquipmentDataCommand addCommand = new AddEquipmentDataCommand();
            addCommand.setEquipmentId((deviceDTO.getEquipmentInfo().getEquipmentId()));
            addCommand.setThresholdId(deviceDTO.getEquipmentInfo().getThresholdId());
            if (thresholdEntity != null) {
                addCommand.setMonitoringIndicator(thresholdEntity.getEquipmentIndex());
            }
            addCommand.setEquipmentData(deviceDTO.getEquipmentInfo().getValue());
            EquipmentDataModel equipmentDataModel = equipmentDataApplicationService.addEquipmentData(addCommand);
            System.out.printf(
                    "添加设备档案的阈值设置=========================================================%s \n",
                    equipmentDataModel.toString());
            // equipmentDataModel.insert();

            // 检查是否报警
            ThresholdValueEntity thresholdValueEntity = thresholdApplicationService.checkEquipmentInfo(
                    deviceDTO.getEquipmentInfo());
            if (thresholdValueEntity == null) {
                return;
            }

            // 添加应急调度下的报警信息
            {
                AddEmergencyAlarmCommand addEmergencyAlarmCommand = new AddEmergencyAlarmCommand();
                addEmergencyAlarmCommand.setLevel(thresholdValueEntity.getLevel());
                addEmergencyAlarmCommand.setDescription(checkEmergencyAlarmDescription(equipmentEntity, thresholdEntity,
                        deviceDTO.getEquipmentInfo().getValue()));
                addEmergencyAlarmCommand.setCode("");
                addEmergencyAlarmCommand.setType("设备报警");
                addEmergencyAlarmCommand.setEquipmentDataId(equipmentDataModel.getEquipmentDataId());
                addEmergencyAlarmCommand
                        .setSopIds(thresholdSopService.getSopIdsByThresholdId(thresholdValueEntity.getThresholdId()));
                addEmergencyAlarmCommand.setEmergencyIds(thresholdEmergencyService
                        .getEmergencyIdsByThresholdId(thresholdValueEntity.getThresholdId()));
                if (addEmergencyAlarmCommand.getSopIds() != null
                        || addEmergencyAlarmCommand.getEmergencyIds() != null) {
                    emergencyAlarmApplicationService.addEmergencyAlarm(addEmergencyAlarmCommand);
                }
            }

            // 添加工艺节点下的报警信息
            {
                QueryWrapper<CraftNodeEntity> queryWrapper = new QueryWrapper<>();
                queryWrapper.inSql("craft_node_id",
                        "select craft_node_id from manage_craft_node_equipment where equipment_id = "
                                + deviceDTO.getEquipmentInfo().getEquipmentId());
                List<CraftNodeEntity> craftNodeEntities = craftNodeService.list(queryWrapper);
                if (craftNodeEntities != null && craftNodeEntities.size() > 0) {
                    for (CraftNodeEntity craftNodeEntity : craftNodeEntities) {
                        command.setEquipmentId(deviceDTO.getEquipmentInfo().getEquipmentId());
                        command.setType("工艺节点报警");
                        command.setLevel(thresholdValueEntity.getLevel());
                        command.setThresholdId(thresholdValueEntity.getThresholdId());
                        command.setEquipmentValue(deviceDTO.getEquipmentInfo().getValue());
                        command.setCraftNodeId(craftNodeEntity.getCraftNodeId());
                        eventApplicationService.createEvent(command);
                    }
                }
            }

            // 添加报警平台的报警信息
            {
                command.setEquipmentId(deviceDTO.getEquipmentInfo().getEquipmentId());
                command.setType("设备报警");
                command.setLevel(thresholdValueEntity.getLevel());
                command.setThresholdId(thresholdValueEntity.getThresholdId());
                command.setEquipmentValue(deviceDTO.getEquipmentInfo().getValue());
                eventApplicationService.createEvent(command);
            }
        }

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
