package com.biology.domain.manage.event.dto;

import java.util.Date;

import org.springframework.beans.BeanUtils;

import com.biology.common.annotation.ExcelColumn;
import com.biology.domain.manage.craftarchive.db.CraftArchiveEntity;
import com.biology.domain.manage.craftnode.db.CraftNodeEntity;
import com.biology.domain.manage.craftnode.dto.CraftNodeDTO;
import com.biology.domain.manage.environment.db.EnvironmentEntity;
import com.biology.domain.manage.environment.dto.EnvironmentDTO;
import com.biology.domain.manage.equipment.db.EquipmentEntity;
import com.biology.domain.manage.equipment.dto.EquipmentDTO;
import com.biology.domain.manage.event.db.EventEntity;
import com.biology.domain.manage.materials.db.MaterialsEntity;
import com.biology.domain.manage.materials.dto.MaterialsDTO;
import com.biology.domain.manage.personnel.db.PersonnelEntity;
import com.biology.domain.manage.personnel.dto.PersonnelDTO;
import com.biology.domain.manage.threshold.db.ThresholdEntity;
import com.biology.domain.manage.threshold.dto.ThresholdDTO;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class EventDeviceNameDTO extends EventDTO {
    @ExcelColumn(name = "设备名称")
    private String equipmentName;

    @ApiModelProperty("事件id")
    @Schema(description = "事件id")
    @ExcelColumn(name = "事件id")
    private Long eventId;

    @ApiModelProperty("报警类型")
    @Schema(description = "报警类型")
    @ExcelColumn(name = "报警类型")
    private String type;

    @ApiModelProperty("设备档案Id")
    @Schema(description = "设备档案Id")
    private Long equipmentId;

    @Schema(description = "设备档案值")
    private double equipmentValue;

    @Schema(description = "设备档案信息")
    private EquipmentDTO equipment;

    @ApiModelProperty("物料Id")
    @Schema(description = "物料Id")
    private Long materialsId;

    @Schema(description = "物料值")
    private Integer materialsValue;

    @Schema(description = "物料信息")
    private MaterialsDTO materials;

    @Schema(description = "环境Id")
    private Long environmentId;

    @Schema(description = "环境信息")
    private EnvironmentDTO environment;

    @Schema(description = "环境值")
    private double environmentValue;

    @Schema(description = "物料阈值Id")
    private Long materialsValueId;

    @ApiModelProperty("报警级别")
    @Schema(description = "报警级别")
    @ExcelColumn(name = "报警级别")
    private String level;

    @ApiModelProperty("报警描述")
    @Schema(description = "报警描述")
    @ExcelColumn(name = "报警描述")
    private String description;

    @ApiModelProperty("处理人Id")
    @Schema(description = "处理人Id")
    private Long handlerId;

    @ApiModelProperty("处理人信息")
    @Schema(description = "处理人信息")
    // @ExcelColumn(name = "处理人信息")
    private PersonnelDTO handler;

    @Schema(description = "环境报警级别Id")
    private Long alarmlevelId;

    @Schema(description = "设备阈值Id")
    private Long thresholdId;

    @Schema(description = "工艺节点Id")
    private Long craftNodeId;

    @Schema(description = "工艺节点信息")
    private CraftNodeDTO craftNode;

    @Schema(description = "阈值信息")
    private ThresholdDTO threshold;

    @ApiModelProperty("创建时间")
    @Schema(description = "创建时间")
    @ExcelColumn(name = "创建时间")
    private Date createTime;

    public EventDeviceNameDTO(EventEntity entity) {
        super(entity);
        if (getEquipment() != null) {
            equipmentName = getEquipment().getEquipmentName();
        }
    }
}
