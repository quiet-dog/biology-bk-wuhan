package com.biology.domain.manage.equipment.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import com.biology.domain.manage.equipment.db.EquipmentMaintenanceManualEntity;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.Date;

@Data
public class EquipmentMaintenanceManualDTO {

    @Schema(description = "手册ID")
    private Long manualId;

    @Schema(description = "设备ID")
    private Long equipmentId;

    @Schema(description = "设备")
    private EquipmentDTO equipment;

    private String equipmentName;

    @Schema(description = "手册版本")
    private String manualVersion;

    @Schema(description = "手册编号")
    private String manualCode;

    @Schema(description = "是否启用")
    private Boolean isEnabled;

    @Schema(description = "维修手册附件路径")
    private JsonNode manualFilePath;

    @Schema(description = "创建时间")
    private Date createTime;

    @Schema(description = "更新时间")
    private Date updateTime;

    @Schema(description = "是否删除")
    private Boolean deleted;

    public EquipmentMaintenanceManualDTO(EquipmentMaintenanceManualEntity entity) {
        BeanUtils.copyProperties(entity, this);
    }
}