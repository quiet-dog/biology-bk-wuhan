package com.biology.domain.manage.craftdisposemanual.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import com.biology.domain.manage.craftarchive.dto.CraftArchiveDTO;
import com.biology.domain.manage.craftdisposemanual.db.DisposeManualEntity;
import com.biology.domain.manage.craftnode.dto.CraftNodeDTO;

@Data
public class DisposeManualDTO {

    @Schema(description = "处置手册ID")
    private Long craftDisposeManualId;

    @Schema(description = "工艺节点ID")
    private Long craftNodeId;

    @Schema(description = "所属工艺档案ID")
    private Long craftArchiveId;

    @Schema(description = "工艺节点")
    private CraftNodeDTO craftNode;

    @Schema(description = "所属工艺档案")
    private CraftArchiveDTO craftArchive;

    @Schema(description = "发生问题")
    private String problemDescription;

    @Schema(description = "紧急处理流程")
    private String emergencyProcess;

    @Schema(description = "责任划分")
    private String responsibilityDivision;

    @Schema(description = "所需时间")
    private String requiredTime;

    @Schema(description = "预防措施")
    private String preventiveMeasures;

    public DisposeManualDTO(DisposeManualEntity entity) {
        BeanUtils.copyProperties(entity, this);
    }
}
