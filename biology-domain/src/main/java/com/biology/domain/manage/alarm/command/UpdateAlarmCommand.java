package com.biology.domain.manage.alarm.command;

import lombok.Data;

@Data
public class UpdateAlarmCommand extends AddAlarmCommand {
    private Long AlarmId;
}
