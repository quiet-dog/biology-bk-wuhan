package com.biology.domain.manage.alarm;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.biology.common.core.page.PageDTO;
import com.biology.domain.manage.alarm.model.AlarmFactory;
import com.biology.domain.manage.alarm.command.AddAlarmCommand;
import com.biology.domain.manage.alarm.command.UpdateAlarmCommand;
import com.biology.domain.manage.alarm.db.AlarmEntity;
import com.biology.domain.manage.alarm.db.AlarmService;
import com.biology.domain.manage.alarm.dto.AlarmDTO;
import com.biology.domain.manage.alarm.model.AlarmModel;
import com.biology.domain.manage.alarm.query.AlarmQuery;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AlarmApplicationService {

    private final AlarmFactory alarmFactory;

    private final AlarmService alarmService;

    public void addAlarm(AddAlarmCommand command) {
        AlarmModel alarmModel = alarmFactory.create();
        alarmModel.loadAddCommand(command);
        alarmModel.insert();
    }

    public void updateAlarm(UpdateAlarmCommand command) {
        AlarmModel alarmModel = alarmFactory.loadById(command.getAlarmId());
        alarmModel.loadAddCommand(command);
        alarmModel.updateById();
    }

    public void deleteAlarm(Long alarmId) {
        AlarmModel alarmModel = alarmFactory.loadById(alarmId);
        alarmModel.deleteById();
    }

    public void deleteAlarms(List<Long> alarmIds) {
        for (Long alarmId : alarmIds) {
            deleteAlarm(alarmId);
        }
    }

    public PageDTO<AlarmDTO> getAlarmList(AlarmQuery query) {
        Page<AlarmEntity> page = alarmService.page(query.toPage(), query.toQueryWrapper());
        List<AlarmDTO> records = page.getRecords().stream().map(AlarmDTO::new).collect(Collectors.toList());
        return new PageDTO<>(records, page.getTotal());
    }

    public AlarmDTO get(Long alarmId) {
        AlarmModel alarmModel = alarmFactory.loadById(alarmId);
        return new AlarmDTO(alarmModel);
    }
}
