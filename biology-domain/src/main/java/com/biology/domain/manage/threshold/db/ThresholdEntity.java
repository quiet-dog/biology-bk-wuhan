package com.biology.domain.manage.threshold.db;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.biology.common.core.base.BaseEntity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@TableName("manage_threshold")
@ApiModel(value = "ThresholdEntity对象", description = "领用阈值表")
public class ThresholdEntity extends BaseEntity<ThresholdEntity> {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("threshold_id")
    @TableId(value = "threshold_id", type = IdType.AUTO)
    private Long thresholdId;

    @ApiModelProperty("设备档案Id")
    @TableField("equipment_id")
    private Long equipmentId;

    @Schema(description = "传感器名称")
    @TableField("sensor_name")
    private String sensorName;

    @Schema(description = "传感器型号")
    @TableField("sensor_model")
    private String sensorModel;

    @Schema(description = "设备指标")
    @TableField("equipment_index")
    private String equipmentIndex;

    @Schema(description = "指标单位")
    @TableField("unit")
    private String unit;

    @ApiModelProperty("code")
    @TableField("code")
    private String code;
    // 购置日期
    @ApiModelProperty("purchase_date")
    @TableField("purchase_date")
    private Date purchaseDate;

    @ApiModelProperty("out_id")
    @TableField("out_id")
    private String outId;

    @Override
    public Serializable pkVal() {
        return this.thresholdId;
    }
}
