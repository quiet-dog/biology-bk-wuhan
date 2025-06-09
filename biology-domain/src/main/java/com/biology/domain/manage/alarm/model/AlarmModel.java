package com.biology.domain.manage.alarm.model;

import java.util.List;

import org.springframework.beans.BeanUtils;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.biology.common.exception.ApiException;
import com.biology.common.exception.error.ErrorCode.Business;
import com.biology.domain.manage.alarm.command.AddAlarmCommand;
import com.biology.domain.manage.alarm.command.UpdateAlarmCommand;
import com.biology.domain.manage.alarm.db.AlarmEntity;
import com.biology.domain.manage.alarm.db.AlarmService;
import com.biology.domain.manage.alarm.dto.MaterialsAlarmDTO;
import com.biology.domain.manage.event.command.AddEventCommand;
import com.biology.domain.manage.event.model.EventFactory;
import com.biology.domain.manage.event.model.EventModel;
import com.biology.domain.manage.materials.db.MaterialsEntity;
import com.biology.domain.manage.materials.db.MaterialsService;
import com.biology.domain.manage.materials.db.MaterialsValueEntity;
import com.biology.domain.manage.materials.db.MaterialsValueService;
import com.biology.domain.manage.notification.command.AddNotificationCommand;
import com.biology.domain.manage.receive.db.ReceiveEntity;
import com.biology.domain.manage.receive.db.ReceiveService;
import com.biology.domain.manage.websocket.db.WebsocketService;
import com.biology.domain.manage.websocket.dto.ContentDTO;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class AlarmModel extends AlarmEntity {

    private AlarmService alarmService;

    private WebsocketService websocketService;

    private MaterialsValueService materialsValueService;

    private ReceiveService receiveService;

    private MaterialsService materialsService;

    private EventFactory eventFactory;

    private double num;

    public AlarmModel(AlarmService alarmService, WebsocketService websocketService,
            MaterialsValueService materialsValueService, ReceiveService receiveService,
            MaterialsService materialsService, EventFactory eventFactory) {
        this.alarmService = alarmService;
        this.websocketService = websocketService;
        this.materialsValueService = materialsValueService;
        this.receiveService = receiveService;
        this.materialsService = materialsService;
        this.eventFactory = eventFactory;
    }

    public AlarmModel(AlarmEntity entity, AlarmService alarmService, WebsocketService websocketService,
            MaterialsValueService materialsValueService, ReceiveService receiveService,
            MaterialsService materialsService, EventFactory eventFactory) {
        if (entity != null) {
            BeanUtils.copyProperties(entity, this);
        }
        this.alarmService = alarmService;
        this.websocketService = websocketService;
        this.materialsValueService = materialsValueService;
        this.receiveService = receiveService;
        this.materialsService = materialsService;
        this.eventFactory = eventFactory;
    }

    public void loadAddCommand(AddAlarmCommand command) {
        if (command != null) {
            BeanUtils.copyProperties(command, this, "alarmId");
        }
    }

    public void loadUpdateCommand(UpdateAlarmCommand command) {
        if (command != null) {
            loadAddCommand(command);
        }
    }

    // 检查剩下的是否出发报警
    public MaterialsValueEntity checkNum() {
        QueryWrapper<ReceiveEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("materials_id", getMaterialsId());
        // 获取receive_num的和
        // double receiveNum =
        // receiveService.getBaseMapper().selectList(queryWrapper).stream()
        // .mapToDouble(ReceiveEntity::getReceiveNum).sum();

        QueryWrapper<MaterialsEntity> materialsDB = new QueryWrapper<>();
        materialsDB.eq("materials_id", getMaterialsId());
        MaterialsEntity materials = materialsService.getBaseMapper().selectOne(materialsDB);
        if (materials == null) {
            throw new ApiException(Business.DOWNLOAD_RECEIVE_FAILED, "物资不存在");
        }
        // 如果相减小于0，抛出异常
        if (materials.getStock() < 0) {
            throw new ApiException(Business.DOWNLOAD_SUBMIT_RECEIVE_FAILED, "库存不足");
        }

        double haveStock = materials.getStock() - getNum();
        if (haveStock < 0) {
            throw new ApiException(Business.DOWNLOAD_SUBMIT_RECEIVE_FAILED, "库存不足");
        }

        setStock(haveStock);
        materials.setStock(haveStock);
        materialsService.getBaseMapper().updateById(materials);
        QueryWrapper<MaterialsValueEntity> queryWrapper2 = new QueryWrapper<>();
        queryWrapper2.eq("materials_id", getMaterialsId());
        List<MaterialsValueEntity> materialsValueEntities = materialsValueService.getBaseMapper()
                .selectList(queryWrapper2);

        MaterialsValueEntity materialsValue = new MaterialsValueEntity();
        if (materialsValueEntities.size() == 0) {
            return null;
        }

        boolean isExit = false;
        for (MaterialsValueEntity materialsValueEntity : materialsValueEntities) {

            // if(materialsValueEntity.getLevel().equals("轻微")){
            // continue;
            // }

            if (materialsValueEntity.getSCondition().equals("大于")
                    && haveStock > materialsValueEntity.getValue()) {
                setLevel(materialsValueEntity.getLevel());
                materialsValue = materialsValueEntity;
                isExit = true;
            }

            if (materialsValueEntity.getSCondition().equals("小于")
                    && haveStock < materialsValueEntity.getValue()) {
                setLevel(materialsValueEntity.getLevel());
                materialsValue = materialsValueEntity;
                isExit = true;
            }

            if (materialsValueEntity.getSCondition().equals("大于等于")
                    && haveStock >= materialsValueEntity.getValue()) {
                setLevel(materialsValueEntity.getLevel());
                materialsValue = materialsValueEntity;
                isExit = true;
            }

            if (materialsValueEntity.getSCondition().equals("小于等于")
                    && haveStock <= materialsValueEntity.getValue()) {
                setLevel(materialsValueEntity.getLevel());
                materialsValue = materialsValueEntity;
                isExit = true;
            }
            if (materialsValueEntity.getSCondition().equals("等于")
                    && haveStock == materialsValueEntity.getValue()) {
                setLevel(materialsValueEntity.getLevel());
                materialsValue = materialsValueEntity;
                isExit = true;
            }
        }

        if (!isExit) {
            return null;
        }
        MaterialsAlarmDTO materialsAlarmDTO = new MaterialsAlarmDTO(materials);
        materialsAlarmDTO.setMaterialsId(getMaterialsId());
        materialsAlarmDTO.setStock(haveStock);
        materialsAlarmDTO.setValue(materialsValue);
        // ContentDTO contentDTO = new ContentDTO();
        // contentDTO.setContent(materialsAlarmDTO);
        // websocketService.sendTopicInfo(contentDTO);

        // 添加报警平台的报警事件
        EventModel eventModel = eventFactory.create();
        AddEventCommand addEventCommand = new AddEventCommand();
        addEventCommand.setMaterialsId(getMaterialsId());
        addEventCommand.setType("物料报警");
        eventModel.setType("物料报警");
        addEventCommand.setLevel(materialsValue.getLevel());
        // addEventCommand.setDescription(
        // materials.getName() + materialsValue.getSCondition() +
        // materialsValue.getValue() + "报警");
        addEventCommand
                .setDescription(String.format("物料编号为%s的%s库存量为%.2f%s,触发报警", materials.getCode(), materials.getName(),
                        materialsAlarmDTO.getStock(), materials.getUnit()));
        eventModel.loadAddEventCommand(addEventCommand);
        eventModel.insert();

        return materialsValue;
    }

    public boolean insert() {
        MaterialsValueEntity result = checkNum();
        if (result == null) {
            return false;
        }

        // MaterialsValueEntity materialsValue = checkNum();
        // if (materialsValue != null) {
        // EventModel eventModel = eventFactory.create();
        // AddEventCommand addEventCommand = new AddEventCommand();
        // addEventCommand.setMaterialsId(getMaterialsId());
        // addEventCommand.setType("物料报警");
        // addEventCommand.setMaterialsValue(getStock());
        // eventModel.loadAddEventCommand(addEventCommand);
        // eventModel.insert();
        // }

        return super.insert();
    }
}
