package com.biology.domain.manage.craftprocess.dto;

import org.springframework.beans.BeanUtils;

import com.biology.domain.manage.craftarchive.dto.CraftArchiveDTO;
import com.biology.domain.manage.craftnode.dto.CraftNodeDTO;
import com.biology.domain.manage.craftprocess.db.CraftProcessEntity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class CraftProcessExcelDTO {
    @Schema(description = "工艺流程图ID")
    // @ExcelColumn(name = "工艺流程图ID", showInImportTemplate = false)
    private Long craftProcessId;

    @Schema(description = "所属工艺档案ID")
    private Long craftArchiveId;

    @Schema(description = "所属工艺档案")
    private CraftArchiveDTO craftArchive;

    @Schema(description = "工艺节点ID")
    private Long craftNodeId;

    @Schema(description = "工艺节点")
    private CraftNodeDTO craftNode;

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

    @Schema(description = "所属工艺档案名称")
    private String craftArchiveName;

    public CraftProcessExcelDTO(CraftProcessDTO entity) {
        BeanUtils.copyProperties(entity, this);
    }
}
