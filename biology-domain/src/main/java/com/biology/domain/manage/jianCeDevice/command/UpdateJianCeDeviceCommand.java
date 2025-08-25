package com.biology.domain.manage.jianCeDevice.command;

import lombok.Data;

@Data
public class UpdateJianCeDeviceCommand extends AddJianCeDeviceCommand {
    private Long jianCeDeviceId;
}
