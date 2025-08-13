package com.biology.domain.manage.xsDevice.command;

import lombok.Data;

@Data
public class UpdateXsDeviceCommand extends AddXsDeviceCommand {
    private Long xsDeviceId;
}
