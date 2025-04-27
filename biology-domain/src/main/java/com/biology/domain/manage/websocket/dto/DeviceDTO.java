package com.biology.domain.manage.websocket.dto;

import com.biology.domain.manage.alarmlevel.dto.EnvironmentAlarmInfoDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class DeviceDTO {
    @Schema(description = "设备类型")
    private String deviceType;

    @Schema(description = "设备ID")
    private Long deviceId;

    @Schema(description = "环境档案数据信息")
    private EnvironmentAlarmInfoDTO environmentAlarmInfo;

    @Schema(description = "设备信息")
    private EquipmentInfoDTO equipmentInfo;

    @Schema(description = "数据时间")
    private String DateSource;

}
