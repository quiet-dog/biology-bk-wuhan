package com.biology.domain.manage.moni.command;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class AddMoniCommand {

    @Schema(description = "描述")
    private String description;

    @Schema(description = "阈值ID")
    private List<List<Long>> thresholdIds;

    @Schema(description = "环境Ids")
    private List<Long> equipmentIds;

    @Schema(description = "环境Ids")
    private List<Long> environmentIds;

    @Schema(description = "最小")
    private Double min;

    @Schema(description = "最大")
    private Double max;

    @Schema(description = "推送类型")
    private String pushType;

    @Schema(description = "推送频率")
    private Integer pushFrequency;

}
