package com.biology.domain.manage.detection.db;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.biology.common.core.base.BaseEntity;

import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@TableName("manage_environment_detection")
@ApiModel(value = "DetectionEntity对象", description = "环境监测表")
public class DetectionEntity extends BaseEntity<DetectionEntity> {
    private static final long serialVersionUID = 1L;

    @TableId(value = "detection_id", type = IdType.AUTO)
    private Long detectionId;

    @TableField("environment_id")
    private Long environmentId;

    @Schema(description = "数值")
    @TableField("value")
    private double value;

    @Schema(description = "功耗")
    @TableField("power")
    private double power;

    @Schema(description = "检测时间")
    @TableField("water_value")
    private double waterValue;

    @Schema(description = "用电量")
    @TableField("electricity_value")
    private double electricityValue;

    @Override
    public Long pkVal() {
        return this.detectionId;
    }
}
