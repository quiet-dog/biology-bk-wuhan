package com.biology.domain.manage.emergencyAlarm.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;

import com.biology.domain.manage.emergencyAlarm.command.AddEmergencyAlarmCommand;
import com.biology.domain.manage.emergencyAlarm.command.UpdateEmergencyAlarmCommand;
import com.biology.domain.manage.emergencyAlarm.db.EmergencyAlarmEntity;
import com.biology.domain.manage.emergencyAlarm.db.EmergencyAlarmService;
import com.biology.domain.manage.emergencyAlarm.db.EmergencyConnectEntity;
import com.biology.domain.manage.emergencyAlarm.db.EmergencyConnectService;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class EmergencyAlarmModel extends EmergencyAlarmEntity {
    private EmergencyAlarmService emergencyAlarmService;

    private EmergencyConnectService emergencyConnectService;

    @Schema(description = "应急预案Ids")
    private List<Long> emergencyIds;

    @Schema(description = "sop的Ids")
    private List<Long> sopIds;

    public EmergencyAlarmModel(EmergencyAlarmService emergencyAlarmService,
            EmergencyConnectService emergencyConnectService) {
        this.emergencyAlarmService = emergencyAlarmService;
        this.emergencyConnectService = emergencyConnectService;
    }

    public EmergencyAlarmModel(EmergencyAlarmEntity emergencyAlarmEntity,
            EmergencyAlarmService emergencyAlarmService, EmergencyConnectService emergencyConnectService) {
        if (emergencyAlarmEntity != null) {
            BeanUtils.copyProperties(emergencyAlarmEntity, this);
        }
        this.emergencyAlarmService = emergencyAlarmService;
        this.emergencyConnectService = emergencyConnectService;
    }

    public void loadAddEmergencyAlarmCommand(AddEmergencyAlarmCommand command) {
        if (command != null) {
            BeanUtils.copyProperties(command, this, "emergencyAlarmId");
        }
    }

    public void loadUpdateEmergencyAlarmCommand(UpdateEmergencyAlarmCommand command) {
        if (command != null) {
            loadAddEmergencyAlarmCommand(command);
        }
    }

    public void addSopIds() {
        if (getSopIds() != null && getSopIds().size() > 0) {
            List<EmergencyConnectEntity> emergencyConnectEntities = new ArrayList<>();
            for (Long sopId : getSopIds()) {
                EmergencyConnectEntity emergencyConnectEntity = new EmergencyConnectEntity();
                emergencyConnectEntity.setEmergencyAlarmId(getEmergencyAlarmId());
                emergencyConnectEntity.setSopId(sopId);
                emergencyConnectEntities.add(emergencyConnectEntity);
            }
            if (emergencyConnectEntities.size() > 0) {
                emergencyConnectService.saveBatch(emergencyConnectEntities);
            }
        }
    }

    public void addEmergencyIds() {
        if (getEmergencyIds() != null && getEmergencyIds().size() > 0) {
            List<EmergencyConnectEntity> emergencyConnectEntities = new ArrayList<>();
            for (Long emergencyId : getEmergencyIds()) {
                EmergencyConnectEntity emergencyConnectEntity = new EmergencyConnectEntity();
                emergencyConnectEntity.setEmergencyAlarmId(getEmergencyAlarmId());
                emergencyConnectEntity.setEmergencyId(emergencyId);
                emergencyConnectEntities.add(emergencyConnectEntity);
            }
            if (emergencyConnectEntities.size() > 0) {
                emergencyConnectService.saveBatch(emergencyConnectEntities);
            }
        }
    }

    public boolean insert() {
        super.insert();
        addSopIds();
        addEmergencyIds();
        return true;
    }

}
