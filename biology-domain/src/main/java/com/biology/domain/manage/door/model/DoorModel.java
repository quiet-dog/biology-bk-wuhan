package com.biology.domain.manage.door.model;

import org.springframework.beans.BeanUtils;

import com.biology.domain.manage.door.command.AddDoorCommand;
import com.biology.domain.manage.door.command.UpdateDoorCommand;
import com.biology.domain.manage.door.db.DoorEntity;
import com.biology.domain.manage.door.db.DoorService;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class DoorModel extends DoorEntity {

    private DoorService doorService;

    public DoorModel(DoorService doorService) {
        this.doorService = doorService;
    }

    public DoorModel(DoorEntity entity, DoorService doorService) {
        if (entity != null) {
            BeanUtils.copyProperties(entity, this);
        }
        this.doorService = doorService;
    }

    public void loadAddDoorCommand(AddDoorCommand command) {
        if (command != null) {
            BeanUtils.copyProperties(command, this, "doorId");
        }
    }

    public void loadUpdateDoorCommand(UpdateDoorCommand command) {
        if (command != null) {
            BeanUtils.copyProperties(command, this, "doorId");
        }
    }
}
