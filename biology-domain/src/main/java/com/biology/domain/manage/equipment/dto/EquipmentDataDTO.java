package com.biology.domain.manage.equipment.dto;

import com.biology.domain.manage.equipment.db.EquipmentDataEntity;
import com.biology.domain.manage.equipment.db.EquipmentEntity;
import com.biology.domain.manage.threshold.db.ThresholdEntity;
import com.biology.domain.manage.threshold.dto.DataThresholdDTO;
import com.biology.domain.manage.threshold.dto.ThresholdDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.util.Date;

@Data
public class EquipmentDataDTO {

    @Schema(description = "设备数据ID")
    private Long equipmentDataId;

    @Schema(description = "设备ID")
    private Long equipmentId;

    @Schema(description = "阈值传感器ID")
    private Long thresholdId;

    @Schema(description = "阈值传感器信息")
    // private ThresholdDTO threshold;DataThresholdDTO
    private DataThresholdDTO threshold;

    @Schema(description = "设备")
    private EquipmentDTO equipment;

    @Schema(description = "监测指标")
    private String monitoringIndicator;

    @Schema(description = "设备数据")
    private double equipmentData;

    @Schema(description = "备注")
    private String remark;

    @Schema(description = "创建时间")
    private Date createTime;

    @Schema(description = "更新时间")
    private Date updateTime;

    @Schema(description = "是否删除")
    private Boolean deleted;

    private String deviceName;

    private String area;

    public EquipmentDataDTO(EquipmentDataEntity entity) {
        if (entity != null) {
            BeanUtils.copyProperties(entity, this);
            if (getThresholdId() != null) {
                this.threshold = new DataThresholdDTO(new ThresholdEntity().selectById(entity.getThresholdId()));
            }
            addDevice();
        }
    }

    public void addDevice() {
        if (getEquipmentId() != null) {
            EquipmentEntity equipmentEntity = new EquipmentEntity().selectById(getEquipmentId());
            if (equipmentEntity != null) {
                setArea(equipmentEntity.getInstallationLocation());
                setDeviceName(equipmentEntity.getEquipmentName());
            }
        }
    }
}