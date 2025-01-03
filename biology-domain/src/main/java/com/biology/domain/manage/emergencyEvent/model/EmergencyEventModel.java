package com.biology.domain.manage.emergencyEvent.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.biology.domain.manage.emergencyAlarm.db.EmergencyAlarmService;
import com.biology.domain.manage.emergencyEvent.command.AddEmergencyEventCommand;
import com.biology.domain.manage.emergencyEvent.command.UpdateEmergencyEventCommand;
import com.biology.domain.manage.emergencyEvent.db.EmergencyEventAlarmEntity;
import com.biology.domain.manage.emergencyEvent.db.EmergencyEventAlarmService;
import com.biology.domain.manage.emergencyEvent.db.EmergencyEventEntity;
import com.biology.domain.manage.emergencyEvent.db.EmergencyEventService;
import com.biology.domain.manage.emergencyEvent.db.EmergencyUserEntity;
import com.biology.domain.manage.emergencyEvent.db.EmergencyUserService;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class EmergencyEventModel extends EmergencyEventEntity {

    private EmergencyEventService emergencyEventService;

    private EmergencyUserService emergencyUserService;

    private EmergencyEventAlarmService emergencyEventAlarmService;

    private List<Long> handleIds;

    private List<Long> emergencyAlarmIds;

    public EmergencyEventModel(EmergencyEventService emergencyEventService, EmergencyUserService emergencyUserService,
            EmergencyEventAlarmService emergencyEventAlarmService) {
        this.emergencyEventService = emergencyEventService;
        this.emergencyUserService = emergencyUserService;
        this.emergencyEventAlarmService = emergencyEventAlarmService;
    }

    public EmergencyEventModel(EmergencyEventEntity entity, EmergencyEventService emergencyEventService,
            EmergencyUserService emergencyUserService, EmergencyEventAlarmService emergencyEventAlarmService) {
        if (entity != null) {
            BeanUtils.copyProperties(entity, this);
        }
        this.emergencyEventService = emergencyEventService;
        this.emergencyUserService = emergencyUserService;
        this.emergencyEventAlarmService = emergencyEventAlarmService;
    }

    public void loadAddEmergencyEventCommand(AddEmergencyEventCommand command) {
        if (command != null) {
            BeanUtils.copyProperties(command, this, "emergencyEventId");
        }
    }

    public void loadUpdateEmergencyEventCommand(UpdateEmergencyEventCommand command) {
        if (command != null) {
            loadAddEmergencyEventCommand(command);
        }
    }

    public void cleanHandler() {
        QueryWrapper<EmergencyUserEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("emergency_event_id", getEmergencyEventId());
        emergencyUserService.remove(queryWrapper);
    }

    public void addHandler() {
        if (getHandleIds() != null) {
            List<EmergencyUserEntity> list = new ArrayList<>();
            for (Long id : getHandleIds()) {
                EmergencyUserEntity emergencyUserEntity = new EmergencyUserEntity();
                emergencyUserEntity.setEmergencyEventId(getEmergencyEventId());
                emergencyUserEntity.setPersonnelId(id);
                list.add(emergencyUserEntity);
            }
            emergencyUserService.saveBatch(list);
        }
    }

    public void addAlarm() {
        if (getEmergencyAlarmIds() != null) {
            List<EmergencyEventAlarmEntity> list = new ArrayList<>();
            for (Long id : getEmergencyAlarmIds()) {
                EmergencyEventAlarmEntity emergencyEventAlarmEntity = new EmergencyEventAlarmEntity();
                emergencyEventAlarmEntity.setEmergencyEventId(getEmergencyEventId());
                emergencyEventAlarmEntity.setEmergencyAlarmId(id);
                list.add(emergencyEventAlarmEntity);
            }
            emergencyEventAlarmService.saveBatch(list);
        }
    }

    public void cleanAlarm() {
        QueryWrapper<EmergencyEventAlarmEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("emergency_event_id", getEmergencyEventId());
        emergencyEventAlarmService.remove(queryWrapper);
    }

    public boolean insert() {
        super.insert();
        addAlarm();
        addHandler();
        return true;
    }

}
