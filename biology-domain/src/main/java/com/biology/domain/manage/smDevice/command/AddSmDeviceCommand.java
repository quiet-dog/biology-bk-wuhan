package com.biology.domain.manage.smDevice.command;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class AddSmDeviceCommand {
    @Schema(description = "设备id")
    private String deviceSn;

    @Schema(description = "设备名称")
    private String name;

    @Schema(description = "人员Id")
    private Long personnelId;

    @Schema(description = "区域")
    private String area;
}
