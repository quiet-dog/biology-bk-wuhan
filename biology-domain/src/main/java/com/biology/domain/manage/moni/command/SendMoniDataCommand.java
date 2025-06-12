package com.biology.domain.manage.moni.command;

import lombok.Data;

@Data
public class SendMoniDataCommand {
    private Long moniId;
    private double value;
}
