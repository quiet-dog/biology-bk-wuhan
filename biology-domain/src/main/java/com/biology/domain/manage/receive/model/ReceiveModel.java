package com.biology.domain.manage.receive.model;

import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.biology.common.exception.ApiException;
import com.biology.common.exception.error.ErrorCode.Business;
import com.biology.domain.manage.alarm.AlarmApplicationService;
import com.biology.domain.manage.alarm.command.AddAlarmCommand;
import com.biology.domain.manage.alarm.db.AlarmService;
import com.biology.domain.manage.alarm.dto.AlarmDTO;
import com.biology.domain.manage.alarm.model.AlarmFactory;
import com.biology.domain.manage.alarm.model.AlarmModel;
import com.biology.domain.manage.materials.db.MaterialsEntity;
import com.biology.domain.manage.materials.db.MaterialsService;
import com.biology.domain.manage.materials.db.MaterialsValueEntity;
import com.biology.domain.manage.materials.db.MaterialsValueService;
import com.biology.domain.manage.receive.command.AddReceiveCommand;
import com.biology.domain.manage.receive.command.UpdateReceiveCommand;
import com.biology.domain.manage.receive.db.ReceiveEntity;
import com.biology.domain.manage.receive.db.ReceiveService;
import com.biology.domain.manage.websocket.db.WebsocketService;

import cn.hutool.core.bean.BeanUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class ReceiveModel extends ReceiveEntity {

    private ReceiveService receiveService;

    private WebsocketService websocketService;

    private MaterialsService materialsService;

    private MaterialsValueService materialsValueService;

    private AlarmApplicationService alarmApplicationService;

    private AlarmService alarmService;

    public ReceiveModel(ReceiveService receiveService, WebsocketService websocketService,
            MaterialsService materialsService, MaterialsValueService materialsValueService, AlarmService alarmService,
            AlarmApplicationService alarmApplicationService) {
        this.receiveService = receiveService;
        this.websocketService = websocketService;
        this.materialsService = materialsService;
        this.materialsValueService = materialsValueService;
        this.alarmService = alarmService;
        this.alarmApplicationService = alarmApplicationService;
    }

    public ReceiveModel(ReceiveEntity entity, ReceiveService receiveService, WebsocketService websocketService,
            MaterialsService materialsService, MaterialsValueService materialsValueService, AlarmService alarmService,
            AlarmApplicationService alarmApplicationService) {
        if (entity != null) {
            BeanUtil.copyProperties(entity, this);
        }
        this.receiveService = receiveService;
        this.websocketService = websocketService;
        this.materialsService = materialsService;
        this.materialsValueService = materialsValueService;
        this.alarmService = alarmService;
        this.alarmApplicationService = alarmApplicationService;
    }

    public void loadAddReceiveCommand(AddReceiveCommand command) {
        if (command != null) {
            BeanUtil.copyProperties(command, this, "receiveId");
        }
    }

    public void loadUpdateReceiveCommand(UpdateReceiveCommand command) {
        if (command != null) {
            loadAddReceiveCommand(command);
        }
    }

    // 计算领用数量
    public void checkReceiveNum() {
        AddAlarmCommand command = new AddAlarmCommand();
        command.setMaterialsId(getMaterialsId());
        command.setNum(getReceiveNum());
        alarmApplicationService.addAlarm(command);
    }

    public boolean insert() {
        checkReceiveNum();
        return super.insert();
    }

}
