package com.biology.domain.manage.materials.command;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class ValueCommand {

    // @ApiModelProperty("物料阈值ID")
    // private Long materialsValueId;

    @ApiModelProperty("物料ID")
    @Schema(description = "物料ID")
    private Long materialsId;

    @ApiModelProperty("报警级别")
    @Schema(description = "报警级别")
    private String level;

    @ApiModelProperty("阈值条件")
    @Schema(description = "阈值条件")
    private String sCondition;

    @ApiModelProperty("指标数值")
    @Schema(description = "指标数值")
    private double value;
}
