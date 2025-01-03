package com.biology.domain.manage.alarmlevel.command;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class AddAlarmlevelCommand {

    @ApiModelProperty("环境档案Id")
    @Schema(description = "环境档案Id")
    private String environmentId;

    @ApiModelProperty("PLC地址")
    @Schema(description = "PLC地址")
    private String plcAddress;

    @ApiModelProperty("启用/禁用")
    @Schema(description = "启用/禁用")
    private boolean enable;

    @ApiModelProperty("级别设置")
    @Schema(description = "级别设置")
    private List<AlarmDetail> details;
}
