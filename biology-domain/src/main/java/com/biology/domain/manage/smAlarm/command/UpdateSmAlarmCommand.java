package com.biology.domain.manage.smAlarm.command;

import lombok.Data;

@Data
public class UpdateSmAlarmCommand extends AddSmAlarmCommand {
    private Long smAlarmId;
}
