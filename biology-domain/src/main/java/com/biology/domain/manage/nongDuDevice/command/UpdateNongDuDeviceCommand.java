package com.biology.domain.manage.nongDuDevice.command;

import lombok.Data;

@Data
public class UpdateNongDuDeviceCommand extends AddNongDuDeviceCommand {
    private Long nongDuDeviceId;
}
