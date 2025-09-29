package com.biology.domain.manage.moni;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.biology.common.core.page.PageDTO;
import com.biology.domain.common.cache.CacheCenter;
import com.biology.domain.manage.alarmlevel.db.AlarmlevelDetailEntity;
import com.biology.domain.manage.alarmlevel.dto.EnvironmentAlarmInfoDTO;
import com.biology.domain.manage.craftnode.db.CraftNodeEntity;
import com.biology.domain.manage.craftnode.db.CraftNodeService;
import com.biology.domain.manage.detection.DetectionApplicationService;
import com.biology.domain.manage.detection.command.AddDetectionCommand;
import com.biology.domain.manage.detection.model.DetectionModel;
import com.biology.domain.manage.emergencyAlarm.EmergencyAlarmApplicationService;
import com.biology.domain.manage.emergencyAlarm.command.AddEmergencyAlarmCommand;
import com.biology.domain.manage.environment.db.EnvironmentEmergencyService;
import com.biology.domain.manage.environment.db.EnvironmentEntity;
import com.biology.domain.manage.environment.db.EnvironmentService;
import com.biology.domain.manage.environment.db.EnvironmentSopService;
import com.biology.domain.manage.equipment.EquipmentApplicationService;
import com.biology.domain.manage.equipment.EquipmentDataApplicationService;
import com.biology.domain.manage.equipment.command.AddEquipmentDataCommand;
import com.biology.domain.manage.equipment.db.EquipmentEntity;
import com.biology.domain.manage.equipment.model.EquipmentDataModel;
import com.biology.domain.manage.event.EventApplicationService;
import com.biology.domain.manage.event.command.AddEventCommand;
import com.biology.domain.manage.healthy.model.HealthyFactory;
import com.biology.domain.manage.materials.db.WarehouseEntity;
import com.biology.domain.manage.materials.dto.WarehouseDTO;
import com.biology.domain.manage.moni.command.AddMoniCommand;
import com.biology.domain.manage.moni.command.SendMoniDataCommand;
import com.biology.domain.manage.moni.command.UpdateMoniCommand;
import com.biology.domain.manage.moni.db.MoniEntity;
import com.biology.domain.manage.moni.db.MoniService;
import com.biology.domain.manage.moni.db.MoniThresholdEntity;
import com.biology.domain.manage.moni.dto.MoniDTO;
import com.biology.domain.manage.moni.model.MoniFactory;
import com.biology.domain.manage.moni.model.MoniModel;
import com.biology.domain.manage.moni.query.SearchMoniQuery;
import com.biology.domain.manage.task.DynamicTaskScheduler;
import com.biology.domain.manage.threshold.ThresholdApplicationService;
import com.biology.domain.manage.threshold.db.ThresholdEmergencyService;
import com.biology.domain.manage.threshold.db.ThresholdEntity;
import com.biology.domain.manage.threshold.db.ThresholdSopService;
import com.biology.domain.manage.threshold.db.ThresholdValueEntity;
import com.biology.domain.manage.websocket.db.WebsocketService;
import com.biology.domain.manage.websocket.dto.DeviceDTO;
import com.biology.domain.manage.websocket.dto.EquipmentInfoDTO;
import com.biology.domain.manage.websocket.dto.OnlineDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MoniApplicationService {

    private final MoniFactory moniFactory;

    private final MoniService moniService;

    private final DynamicTaskScheduler dynamicTaskScheduler;

    private final EventApplicationService eventApplicationService;

    private final ThresholdApplicationService thresholdApplicationService;

    // private final EquipmentApplicationService equipmentApplicationService;

    private final WebsocketService websocketService;

    private final DetectionApplicationService detectionApplicationService;

    private final EquipmentDataApplicationService equipmentDataApplicationService;

    private final EmergencyAlarmApplicationService emergencyAlarmApplicationService;

    private final ThresholdSopService thresholdSopService;

    private final ThresholdEmergencyService thresholdEmergencyService;

    private final EnvironmentSopService environmentSopService;

    private final EnvironmentEmergencyService environmentEmergencyService;

    private final CraftNodeService craftNodeService;

    private final WebClient opcClient;

    private final EnvironmentService environmentService;

    // private final HealthyFactory healthyFactory;

    public void addMoni(AddMoniCommand command) {
        MoniModel moniModel = moniFactory.create();
        moniModel.loadAddMoniCommand(command);
        moniModel.insert();
    }

    public void updateMoni(UpdateMoniCommand command) {
        MoniModel moniModel = moniFactory.loadById(command.getMoniId());
        moniModel.loadUpdateMoniCommand(command);
        moniModel.updateById();
    }

    public void deleteMoni(List<Long> moniIds) {
        for (Long moniId : moniIds) {
            stopPush(moniId);
            MoniModel moniModel = moniFactory.loadById(moniId);
            moniModel.deleteById();
        }
    }

    public PageDTO<MoniDTO> getMoniList(SearchMoniQuery query) {
        Page<MoniEntity> page = moniService.page(query.toPage(), query.toQueryWrapper());
        List<MoniDTO> records = page.getRecords().stream().map(MoniDTO::new).collect(Collectors.toList());
        return new PageDTO<>(records, page.getTotal());
    }

    public void startPush(Long moniId) {
        MoniModel moniModel = moniFactory.loadById(moniId);
        moniModel.setIsPush(true);
        moniModel.updateById();
        // 将id转为string
        String moniIdStr = String.valueOf(moniId);
        dynamicTaskScheduler.addTask(moniIdStr, () -> {
            // 生成随机浮点数在 min 和 max中
            QueryWrapper<MoniThresholdEntity> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("moni_id", moniId);
            List<MoniThresholdEntity> moniThresholdEntities = new MoniThresholdEntity().selectList(queryWrapper);
            System.out.printf(
                    "开始推送模拟数据========================================================= \n");
            System.out.printf("推送频率为：%s \n", moniModel.getPushFrequency());
            for (MoniThresholdEntity moniThresholdEntity : moniThresholdEntities) {
                DeviceDTO dto = new DeviceDTO();
                double min = moniModel.getMin();
                double max = moniModel.getMax();
                double randomValue = min + (Math.random() * (max - min));
                // 保留1位小数
                randomValue = Math.round(randomValue * 10.0) / 10.0;
                // 保留2位小数
                // randomValue = Math.round(randomValue * 100.0) / 100.0;

                if (moniThresholdEntity.getEnvironmentId() != null && moniThresholdEntity.getEnvironmentId() != 0) {
                    dto.setDeviceType("环境档案");
                    EnvironmentAlarmInfoDTO eDto = new EnvironmentAlarmInfoDTO();
                    EnvironmentEntity environmentEntity = environmentService
                            .getById(moniThresholdEntity.getEnvironmentId());

                    eDto.setEnvironmentId(moniThresholdEntity.getEnvironmentId());
                    // if (environmentEntity.getUnitName() != null) {
                    // if (environmentEntity.getUnitName().equals("电")) {
                    // eDto.setElectricityValue(randomValue);
                    // } else if (environmentEntity.getUnitName().equals("水")) {
                    // eDto.setWaterValue(randomValue);
                    // } else {
                    // }
                    // }
                    eDto.setValue(randomValue);

                    // eDto.setValue(randomValue);
                    dto.setEnvironmentAlarmInfo(eDto);
                }

                if (moniThresholdEntity.getThresholdId() != null && moniThresholdEntity.getThresholdId() != 0) {
                    dto.setDeviceType("设备档案");
                    EquipmentInfoDTO eDto = new EquipmentInfoDTO();
                    eDto.setThresholdId(moniThresholdEntity.getThresholdId());
                    eDto.setValue(randomValue);
                    dto.setEquipmentInfo(eDto);
                }
                sendMsg(dto);
                System.out.printf("推送数据为：%s \n", dto.toString());
            }

        }, 0, moniModel.getPushFrequency(), TimeUnit.SECONDS);
    }

    public void stopPush(Long moniId) {
        // 将id转为string
        String moniIdStr = String.valueOf(moniId);
        dynamicTaskScheduler.removeTask(moniIdStr);
        MoniModel moniModel = moniFactory.loadById(moniId);
        moniModel.setIsPush(false);
        moniModel.updateById();
    }

    public void sendMsg(DeviceDTO deviceDTO) {

        websocketService.sendTopicData(deviceDTO);
        try {
            sendOpc(deviceDTO);
        } catch (Exception e) {
        }

        System.out.printf("发送数据为：%s \n", deviceDTO.toString());
        AddEventCommand command = new AddEventCommand();
        OnlineDTO oDto = new OnlineDTO();
        oDto.setTime(System.currentTimeMillis());
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
            if (environmentEntity.getUnitName() != null) {
                if (environmentEntity.getUnitName().equals("电")) {
                    addDetectionCommand.setElectricityValue(deviceDTO.getEnvironmentAlarmInfo().getValue());
                } else if (environmentEntity.getUnitName().equals("水")) {
                    addDetectionCommand.setWaterValue(deviceDTO.getEnvironmentAlarmInfo().getValue());
                } else {
                    addDetectionCommand.setValue(deviceDTO.getEnvironmentAlarmInfo().getValue());
                }
            } else {
                addDetectionCommand.setValue(deviceDTO.getEnvironmentAlarmInfo().getValue());
            }

            // addDetectionCommand.setPower(deviceDTO.getEnvironmentAlarmInfo().getPower());
            // addDetectionCommand.setWaterValue(deviceDTO.getEnvironmentAlarmInfo().getWaterValue());
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
                // 位号为%s的%s-%s数值为%.2f,触发报警

                command.setDescription(String.format("位号为%s的%s-%s数值为%.2f,触发报警", environmentEntity.getTag(),
                        environmentEntity.getDescription(), environmentEntity.getUnitName(),
                        deviceDTO.getEnvironmentAlarmInfo().getValue()));
                eventApplicationService.createEvent(command);
            }
        }

        if (deviceDTO.getDeviceType().equals("设备档案")) {
            QueryWrapper<ThresholdEntity> queryWrapper1 = new QueryWrapper<>();
            queryWrapper1.eq("threshold_id",
                    deviceDTO.getEquipmentInfo().getThresholdId());
            ThresholdEntity thresholdEntity = new ThresholdEntity().selectOne(queryWrapper1);
            deviceDTO.getEquipmentInfo().setEquipmentId(thresholdEntity.getEquipmentId());

            if (deviceDTO.getEquipmentInfo().getThresholdId() != null) {
                System.out.printf(
                        "进入到设备档案获取阈值设置当中========================================================= \n");
                redisId = "threshold-" + deviceDTO.getEquipmentInfo().getThresholdId();
                oDto.setIsOnline(true);
                oDto.setThresholdData(deviceDTO.getEquipmentInfo().getValue());
                oDto.setThresholdId(deviceDTO.getEquipmentInfo().getThresholdId());
                CacheCenter.onlineCache.set(redisId, oDto);

                if (deviceDTO.getEquipmentInfo().getEquipmentId() != null) {
                    List<ThresholdEntity> l = new ThresholdEntity().selectList(new QueryWrapper<ThresholdEntity>()
                            .eq("equipment_id", deviceDTO.getEquipmentInfo().getEquipmentId()));
                    redisId = "equipment-" + deviceDTO.getEquipmentInfo().getEquipmentId();
                    Boolean isYiChang = false;
                    oDto.setEquipmentId(deviceDTO.getEquipmentInfo().getEquipmentId());
                    if (l != null && l.size() > 0) {
                        for (Integer i = 0; i < l.size(); i++) {
                            String tid = "threshold-" + l.get(i).getThresholdId().toString();
                            OnlineDTO o = CacheCenter.onlineCache.getObjectById(tid);
                            if (o == null) {
                                isYiChang = true;
                                break;
                            }
                        }
                    } else {
                        isYiChang = true;
                    }
                    oDto.setIsException(isYiChang);
                    CacheCenter.onlineCache.set(redisId, oDto);
                }
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
                command.setDescription(String.format("设备编号为%s的%s-%s为%.2f,触发报警", equipmentEntity.getEquipmentCode(),
                        equipmentEntity.getEquipmentName(), thresholdEntity.getEquipmentIndex(),
                        deviceDTO.getEquipmentInfo().getValue()));
                eventApplicationService.createEvent(command);
            }
        }

        return;
    }

    public void sendOpc(DeviceDTO deviceDTO) {
        // 只发送，不处理响应
        opcClient.post()
                .uri("/api/recDataApi")
                .bodyValue(deviceDTO)
                .retrieve()
                .bodyToMono(String.class)
                .subscribe(response -> {
                });
    }

    public String checkEnvironmentDescription(EnvironmentEntity environmentEntity,
            double value) {
        return String.format("位号为%s的%s-%s数值为%.2f,触发报警", environmentEntity.getTag(),
                environmentEntity.getDescription(), environmentEntity.getUnitName(), value);
    }

    public String checkEmergencyAlarmDescription(EquipmentEntity equipmentEntity, ThresholdEntity thresholdEntity,
            double value) {
        return String.format("设备编号为%s的%s-%s为%.2f,触发报警", equipmentEntity.getEquipmentCode(),
                equipmentEntity.getEquipmentName(), thresholdEntity.getEquipmentIndex(), value);
    }

    public void sendData(SendMoniDataCommand data) {
        MoniThresholdEntity moniThresholdEntity = new MoniThresholdEntity();
        List<MoniThresholdEntity> mEntities = moniThresholdEntity.selectList(
                new QueryWrapper<MoniThresholdEntity>().eq("moni_id", data.getMoniId()));

        for (MoniThresholdEntity moniThreshold : mEntities) {
            DeviceDTO deviceDTO = new DeviceDTO();
            if (moniThreshold.getEnvironmentId() != null && moniThreshold.getEnvironmentId() != 0) {
                deviceDTO.setDeviceType("环境档案");
                EnvironmentEntity environmentEntity = environmentService
                        .getById(moniThreshold.getEnvironmentId());
                EnvironmentAlarmInfoDTO eDto = new EnvironmentAlarmInfoDTO();
                eDto.setEnvironmentId(moniThreshold.getEnvironmentId());
                if (environmentEntity.getUnitName() != null) {
                    if (environmentEntity.getUnitName().equals("电")) {
                        eDto.setElectricityValue(data.getValue());
                    } else if (environmentEntity.getUnitName().equals("水")) {
                        eDto.setWaterValue(data.getValue());
                    } else {
                        eDto.setValue(data.getValue());
                    }
                }
                deviceDTO.setEnvironmentAlarmInfo(eDto);
            }

            if (moniThreshold.getThresholdId() != null && moniThreshold.getThresholdId() != 0) {
                deviceDTO.setDeviceType("设备档案");
                EquipmentInfoDTO eDto = new EquipmentInfoDTO();
                eDto.setThresholdId(moniThreshold.getThresholdId());
                eDto.setValue(data.getValue());
                deviceDTO.setEquipmentInfo(eDto);
            }
            sendMsg(deviceDTO);
        }
    }

}
