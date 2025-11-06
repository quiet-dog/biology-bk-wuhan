package com.biology.domain.manage.event.dto;

import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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
import com.biology.domain.manage.threshold.db.ThresholdService;
import com.biology.domain.manage.threshold.dto.ThresholdDTO;
import com.biology.domain.manage.threshold.query.ThresholdSearch;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class EventDTO {
    @ApiModelProperty("事件id")
    @Schema(description = "事件id")
    @ExcelColumn(name = "报警编号")
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

    @ApiModelProperty("推送类型")
    @Schema(description = "推送类型")
    private String pushType;

    @ApiModelProperty("创建时间")
    @Schema(description = "创建时间")
    @ExcelColumn(name = "报警时间")
    private Date createTime;

    private String nodeName;

    public EventDTO(EventEntity entity) {
        if (entity != null) {
            BeanUtils.copyProperties(entity, this);
            // if (getHandlerId() != null) {
            // PersonnelEntity personnelEntity = new PersonnelEntity();
            // personnelEntity = personnelEntity.selectById(getHandlerId());
            // if (personnelEntity != null) {
            // setHandler(new PersonnelDTO(personnelEntity));
            // }
            // }

            // if (getEquipmentId() != null) {
            // EquipmentEntity equipmentEntity = new EquipmentEntity();
            // equipmentEntity = equipmentEntity.selectById(getEquipmentId());
            // if (equipmentEntity != null) {
            // setEquipment(new EquipmentDTO(equipmentEntity));
            // }
            // }

            // if (getMaterialsId() != null) {
            // MaterialsEntity materialsEntity = new MaterialsEntity();
            // materialsEntity = materialsEntity.selectById(getMaterialsId());
            // if (materialsEntity != null) {
            // setMaterials(new MaterialsDTO(materialsEntity));
            // }
            // }
            // if (getEnvironmentId() != null) {
            // EnvironmentEntity environmentEntity = new EnvironmentEntity();
            // environmentEntity = environmentEntity.selectById(getEnvironmentId());
            // if (environmentEntity != null) {
            // setEnvironment(new EnvironmentDTO(environmentEntity));
            // }
            // }

            // if (getThresholdId() != null) {
            // ThresholdEntity thresholdEntity = new ThresholdEntity();
            // thresholdEntity = thresholdEntity.selectById(getThresholdId());
            // if (thresholdEntity != null) {
            // setThreshold(new ThresholdDTO(thresholdEntity));
            // }
            // }

            // if (getCraftNodeId() != null) {
            // CraftNodeEntity craftNodeEntity = new CraftNodeEntity();
            // craftNodeEntity = craftNodeEntity.selectById(getCraftNodeId());
            // if (craftNodeEntity != null) {
            // setCraftNode(new CraftNodeDTO(craftNodeEntity));
            // }
            // }

            // addDescription();
        }
    }

    public void addDescription() {
        if (getType() != null && getDescription() == null && getType().equals("设备报警") && getEquipment() != null
                && getThreshold() != null) {
            setDescription(String.format("设备编号为%s的%s-%s为%.2f,触发报警",
                    getEquipment().getEquipmentCode(),
                    getEquipment().getEquipmentName(), getThreshold().getEquipmentIndex(),
                    getEquipmentValue()));
        }

        if (getType() != null && getDescription() == null && getType().equals("环境报警") && environment != null
                && environment.getAlarmlevels() != null) {
            environment.getAlarmlevels().forEach(alarmlevel -> {
                if (alarmlevel.getMin() < environmentValue && environmentValue < alarmlevel.getMax()) {
                    setDescription(String.format("位号为%s的%s-%s数值为%.2f,触发报警", environment.getTag(),
                            environment.getDescription(), environment.getUnitName(),
                            getEnvironmentValue()));
                }
            });
            return;
        }

        if (getType() != null && getType().equals("物料报警") && materials != null && materials.getValues() != null
                && materialsValue != null) {

            setDescription(String.format("物料编号为%s的%s库存量为%.2f%s,触发报警", materials.getCode(), materials.getName(),
                    materialsValue, materials.getUnit()));
            // materials.getValues().forEach(materialsV -> {
            // if (materialsV.getSCondition().equals("大于") && materialsValue >
            // materialsV.getValue()) {
            // setDescription(String.format("%s库存量大于%.2f报警", materials.getName(),
            // materialsV.getValue()));
            // }
            // if (materialsV.getSCondition().equals("小于")) {
            // setDescription(String.format("%s库存量小于%.2f报警", materials.getName(),
            // materialsV.getValue()));
            // }
            // if (materialsV.getSCondition().equals("大于等于")) {
            // setDescription(String.format("%s库存量大于等于%.2f报警", materials.getName(),
            // materialsV.getValue()));
            // }
            // if (materialsV.getSCondition().equals("等于")) {
            // setDescription(String.format("%s库存量等于%.2f报警", materials.getName(),
            // materialsV.getValue()));
            // }
            // });
            return;
        }

        if (getType() != null && getType().equals("工艺节点报警") && craftNode != null) {
            CraftArchiveEntity craftArchiveEntity = new CraftArchiveEntity().selectById(craftNode.getCraftArchiveId());
            if (craftArchiveEntity == null) {
                return;
            }

            setDescription(String.format("%s的%s工艺节点故障,触发报警", craftNode.getNodeName(),
                    craftArchiveEntity.getCraftArchiveName()));
        }
    }
}
