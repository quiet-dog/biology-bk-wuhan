package com.biology.domain.manage.craftnode.dto;

import com.biology.domain.manage.craftarchive.dto.CraftArchiveDTO;
import com.biology.domain.manage.craftnode.db.CraftNodeEntity;
import com.biology.domain.manage.equipment.db.EquipmentEntity;
import com.biology.domain.manage.equipment.dto.EquipmentDTO;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;

@Data
public class CraftNodeDTO {

    @ApiModelProperty("工艺节点ID")
    private Long craftNodeId;

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

    @Schema(description = "所属工艺档案")
    private CraftArchiveDTO craftArchive;

    @Schema(description = "关联的设备列表")
    private List<EquipmentDTO> equipmentList;

    @Schema(description = "设备ID列表")
    private List<Long> equipmentIds;

    @Schema(description = "标签名称")
    private String labelName;

    @Schema(description = "标签颜色")
    private String color;

    @Schema(description = "标签描述")
    private String colorDescription;

    public CraftNodeDTO(CraftNodeEntity entity) {
        BeanUtils.copyProperties(entity, this);
    }
}
