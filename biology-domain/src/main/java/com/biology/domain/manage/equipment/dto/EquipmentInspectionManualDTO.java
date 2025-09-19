package com.biology.domain.manage.equipment.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;

import org.springframework.beans.BeanUtils;

import com.biology.domain.manage.equipment.db.EquipmentEntity;
import com.biology.domain.manage.equipment.db.EquipmentInspectionManualEntity;
import com.biology.domain.manage.equipment.model.EquipmentModel;
import com.fasterxml.jackson.databind.JsonNode;

@Data
public class EquipmentInspectionManualDTO {

    @Schema(description = "手册ID")
    private Long manualId;

    @Schema(description = "设备ID")
    private Long equipmentId;

    @Schema(description = "设备")
    private EquipmentDTO equipment;

    // @Schema(description = "设备类型")
    // private String equipmentType;

    // @Schema(description = "设备编号")
    // private String equipmentCode;

    @Schema(description = "设备名称")
    private String equipmentName;

    @Schema(description = "适用范围")
    private String suitableScope;

    @Schema(description = "手册版本")
    private String manualVersion;

    @Schema(description = "手册编号")
    private String manualCode;

    @Schema(description = "是否启用")
    private Boolean isEnabled;

    @Schema(description = "检修手册附件路径")
    private JsonNode manualFilePath;

    @Schema(description = "创建时间")
    private Date createTime;

    @Schema(description = "更新时间")
    private Date updateTime;

    @Schema(description = "是否删除")
    private Boolean deleted;

    @Schema(description = "手册名称")
    private String manualName;

    public EquipmentInspectionManualDTO(EquipmentInspectionManualEntity entity) {
        BeanUtils.copyProperties(entity, this);

        if (getEquipmentId() != null) {
            EquipmentEntity equipmentModel = new EquipmentEntity().selectById(getEquipmentId());
            setEquipment(new EquipmentDTO(equipmentModel));
        }
        if (getEquipment() != null) {
            setEquipmentName(equipment.getEquipmentName());
        }
    }
}