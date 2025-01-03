package com.biology.domain.manage.craftnode.command;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class AddCraftNodeCommand {

    @Schema(description = "工艺节点名称")
    private String nodeName;

    @Schema(description = "节点编号")
    private String nodeCode;

    @Schema(description = "所属工艺档案ID")
    private Long craftArchiveId;

    @Schema(description = "节点顺序")
    private Integer nodeOrder;

    @Schema(description = "所需时间")
    private String requiredTime;

    @Schema(description = "节点标签")
    private String nodeTags;

    @Schema(description = "是否为高风险")
    private Boolean isHighRisk;

    @Schema(description = "操作描述")
    private String operationDescription;

    @Schema(description = "操作方法")
    private String operationMethod;

    @Schema(description = "设备ID列表")
    private List<Long> equipmentIds;
}
