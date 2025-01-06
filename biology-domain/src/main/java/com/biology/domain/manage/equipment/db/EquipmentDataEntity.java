package com.biology.domain.manage.equipment.db;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.biology.common.core.base.BaseEntity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@TableName(value = "manage_equipment_data", autoResultMap = true)
public class EquipmentDataEntity extends BaseEntity<EquipmentDataEntity> {
    @Schema(description = "设备数据ID")
    @TableId(value = "equipment_data_id", type = IdType.AUTO)
    private Long equipmentDataId;

    @Schema(description = "设备ID")
    @TableField("equipment_id")
    private Long equipmentId;

    @Schema(description = "阈值传感器ID")
    @TableField("threshold_id")
    private Long thresholdId;

    @Schema(description = "监测指标")
    @TableField("monitoring_indicator")
    private String monitoringIndicator;

    @Schema(description = "设备数据")
    @TableField("equipment_data")
    private double equipmentData;

    @Schema(description = "备注")
    @TableField("remark")
    private String remark;

    // @Schema(description = " 创建时间")
    // @TableField("create_time")
    // private Date createTime;

    // @Schema(description = "更新时间")
    // @TableField("update_time")
    // private Date updateTime;

    // @Schema(description = "删除标志")
    // @TableField("deleted")
    // private Boolean deleted;
}
