package com.biology.domain.manage.craftdisposemanual.command;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@ApiModel(value = "AddDisposeManualCommand", description = "添加处置手册命令")
public class AddDisposeManualCommand {

    @ApiModelProperty("工艺节点")
    @Schema(description = "工艺节点ID")
    private Long craftNodeId;

    @ApiModelProperty("所属工艺档案ID")
    @Schema(description = "所属工艺档案ID")
    private Long craftArchiveId;

    @ApiModelProperty("发生问题")
    @Schema(description = "发生问题")
    private String problemDescription;

    @ApiModelProperty("紧急处理流程")
    @Schema(description = "紧急处理流程")
    private String emergencyProcess;

    @ApiModelProperty("责任划分")
    @Schema(description = "责任划分")
    private String responsibilityDivision;

    @ApiModelProperty("所需时间")
    @Schema(description = "所需时间")
    private String requiredTime;

    @ApiModelProperty("预防措施")
    @Schema(description = "预防措施")
    private String preventiveMeasures;
}
