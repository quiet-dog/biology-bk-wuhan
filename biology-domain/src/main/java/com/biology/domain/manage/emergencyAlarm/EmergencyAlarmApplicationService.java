package com.biology.domain.manage.emergencyAlarm;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.biology.common.core.page.PageDTO;
import com.biology.domain.manage.emergencyAlarm.command.AddEmergencyAlarmCommand;
import com.biology.domain.manage.emergencyAlarm.command.UpdateEmergencyAlarmCommand;
import com.biology.domain.manage.emergencyAlarm.db.EmergencyAlarmEntity;
import com.biology.domain.manage.emergencyAlarm.db.EmergencyAlarmService;
import com.biology.domain.manage.emergencyAlarm.dto.EmergencyAlarmDTO;
import com.biology.domain.manage.emergencyAlarm.model.EmergencyAlarmFactory;
import com.biology.domain.manage.emergencyAlarm.model.EmergencyAlarmModel;
import com.biology.domain.manage.emergencyAlarm.query.EmergencyAlarmQuery;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmergencyAlarmApplicationService {
    private final EmergencyAlarmFactory emergencyAlarmFactory;

    private final EmergencyAlarmService emergencyAlarmService;

    public void addEmergencyAlarm(AddEmergencyAlarmCommand command) {
        EmergencyAlarmModel emergencyAlarmModel = emergencyAlarmFactory.create();
        emergencyAlarmModel.loadAddEmergencyAlarmCommand(command);
        emergencyAlarmModel.insert();
    }

    public void updateEmergencyAlarm(UpdateEmergencyAlarmCommand command) {
        EmergencyAlarmModel emergencyAlarmModel = emergencyAlarmFactory.loadById(command.getEmergencyAlarmId());
        emergencyAlarmModel.loadUpdateEmergencyAlarmCommand(command);
        emergencyAlarmModel.updateById();
    }

    public void deleteEmergencyAlarms(List<Long> emergencyAlarmIds) {
        for (Long emergencyAlarmId : emergencyAlarmIds) {
            deleteEmergencyAlarm(emergencyAlarmId);
        }
    }

    public void deleteEmergencyAlarm(Long emergencyAlarmId) {
        EmergencyAlarmModel emergencyAlarmModel = emergencyAlarmFactory.loadById(emergencyAlarmId);
        emergencyAlarmModel.deleteById();
    }

    public EmergencyAlarmDTO getEmergencyAlarm(Long emergencyAlarmId) {
        EmergencyAlarmModel emergencyAlarmModel = emergencyAlarmFactory.loadById(emergencyAlarmId);
        return new EmergencyAlarmDTO(emergencyAlarmModel);
    }

    public PageDTO<EmergencyAlarmDTO> searchEmergencyAlarms(EmergencyAlarmQuery query) {
        Page<EmergencyAlarmEntity> page = emergencyAlarmService.page(query.toPage(), query.toQueryWrapper());
        List<EmergencyAlarmDTO> records = page.getRecords().stream().map(EmergencyAlarmDTO::new)
                .collect(Collectors.toList());
        return new PageDTO<>(records, page.getTotal());
    }
}
