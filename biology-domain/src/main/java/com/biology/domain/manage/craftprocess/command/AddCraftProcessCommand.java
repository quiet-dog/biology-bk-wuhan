package com.biology.domain.manage.craftprocess.command;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class AddCraftProcessCommand {

    @Schema(description = "所属工艺档案ID")
    private Long craftArchiveId;

    @Schema(description = "工艺节点ID")
    private Long craftNodeId;

    @Schema(description = "节点顺序")
    private Integer nodeOrder;

    @Schema(description = "人员要素")
    private String personnelFactors;

    @Schema(description = "设备要素")
    private String equipmentFactors;

    @Schema(description = "原料要素")
    private String materialFactors;

    @Schema(description = "环境要素")
    private String environmentFactors;
}
