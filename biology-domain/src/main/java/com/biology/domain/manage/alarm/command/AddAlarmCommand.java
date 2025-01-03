package com.biology.domain.manage.alarm.command;

import lombok.Data;

@Data
public class AddAlarmCommand {
    private Long materialsId;

    private double num;
}
