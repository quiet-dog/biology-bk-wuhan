package com.biology.domain.manage.emergencyAlarm.command;

import java.util.List;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class AddEmergencyAlarmCommand {

    @ApiModelProperty("应急编号")
    @Schema(description = "应急编号")
    private String code;

    @ApiModelProperty("报警类型")
    @Schema(description = "报警类型")
    private String type;

    @ApiModelProperty("报警描述")
    @Schema(description = "报警描述")
    private String description;

    @Schema(description = "报警级别")
    private String level;

    @Schema(description = "设备数据id")
    private Long equipmentDataId;

    @Schema(description = "环境数据Id")
    private Long detectionId;

    private Long environmentId;

    @Schema(description = "应急预案Ids")
    private List<Long> emergencyIds;

    @Schema(description = "sop的Ids")
    private List<Long> sopIds;
}
