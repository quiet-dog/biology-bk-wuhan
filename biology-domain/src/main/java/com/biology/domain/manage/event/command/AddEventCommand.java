package com.biology.domain.manage.event.command;

import java.util.List;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.biology.common.annotation.ExcelColumn;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class AddEventCommand {

    @ApiModelProperty("报警类型")
    private String type;

    @ApiModelProperty("报警级别")
    private String level;

    @ApiModelProperty("工艺节点Id")
    private Long craftNodeId;

    @ApiModelProperty("设备档案Id")
    private Long equipmentId;

    @Schema(description = "设备档案单位")
    private String equipmentUnit;

    @Schema(description = "设备档案值")
    private double equipmentValue;

    @ApiModelProperty("物料Id")
    private Long materialsId;

    @Schema(description = "物料名称值")
    private double materialsValue;

    @Schema(description = "物料单位")
    private String materialsUnit;

    @ApiModelProperty("物料阈值Id")
    private Long environmentId;

    @Schema(description = "环境档案单位")
    private String environmentUnit;

    private double environmentValue;

    @Schema(description = "环境报警级别Id")
    private Long alarmlevelId;

    @Schema(description = "设备阈值Id")
    private Long thresholdId;

    @ApiModelProperty("报警描述")
    private String description;

    @ApiModelProperty("处理人Id")
    private Long handlerId;

    @ApiModelProperty("处理文件")
    private List<String> paths;

    @ApiModelProperty("推送类型")
    @ExcelColumn(name = "推送类型")
    private String pushType;

    private String nodeName;
}
