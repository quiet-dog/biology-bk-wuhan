package com.biology.domain.manage.door;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.biology.common.core.page.PageDTO;
import com.biology.domain.manage.alarmlevel.db.AlarmlevelDetailService;
import com.biology.domain.manage.alarmlevel.db.AlarmlevelService;
import com.biology.domain.manage.environment.db.EnvironmentService;
import com.biology.domain.manage.door.command.AddDoorCommand;
import com.biology.domain.manage.door.command.UpdateDoorCommand;
import com.biology.domain.manage.door.db.DoorEntity;
import com.biology.domain.manage.door.db.DoorService;
import com.biology.domain.manage.door.dto.DoorDTO;
import com.biology.domain.manage.door.model.DoorFactory;
import com.biology.domain.manage.door.model.DoorModel;
import com.biology.domain.manage.door.query.DoorQuery;
import com.biology.domain.manage.materials.db.MaterialsValueService;
import com.biology.domain.manage.threshold.db.ThresholdValueService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DoorApplicationService {

    private final DoorFactory doorFactory;

    private final DoorService doorService;

    public void createDoor(AddDoorCommand command) {
        DoorModel doorModel = doorFactory.create();
        doorModel.loadAddDoorCommand(command);
        doorModel.insert();
    }

    public void updateDoor(UpdateDoorCommand command) {
        DoorModel doorModel = doorFactory.loadById(command.getDoorId());
        doorModel.loadUpdateDoorCommand(command);
        doorModel.updateById();
    }

    public void deleteEmergencies(List<Long> doorIds) {
        for (Long doorId : doorIds) {
            DoorModel doorModel = doorFactory.loadById(doorId);
            doorModel.deleteById();
        }
    }

    public DoorDTO getDoorInfo(Long doorId) {
        DoorEntity doorEntity = doorService.getById(doorId);

        return new DoorDTO(doorEntity);
    }

    public PageDTO<DoorDTO> getDoorList(DoorQuery query) {
        Page<DoorEntity> page = doorService.page(query.toPage(), query.toQueryWrapper());
        List<DoorDTO> records = page.getRecords().stream().map(DoorDTO::new).collect(Collectors.toList());
        return new PageDTO<>(records, page.getTotal());
    }

    public Double getDayStatus() {
        return doorService.getDayStatus();
    }

}
