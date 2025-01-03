package com.biology.domain.manage.healthy.db;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.biology.common.core.base.BaseEntity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@TableName("manage_personnel_healthy")
@ApiModel(value = "HealthyEntity对象", description = "人员健康信息表")
public class HealthyEntity extends BaseEntity<HealthyEntity> {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("健康Id")
    @TableId(value = "healthy_id", type = IdType.AUTO)
    private Long healthyId;

    @ApiModelProperty("人员Id")
    @TableField("personnel_id")
    private Long personnelId;

    @ApiModelProperty("体温")
    @TableField("temperature")
    private double temperature;

    @ApiModelProperty("心率")
    @TableField("heart_rate")
    private double heartRate;

    @ApiModelProperty("收缩压")
    @TableField("high_blood_pressure")
    private double highBloodPressure;

    @ApiModelProperty("舒张压")
    @TableField("low_blood_pressure")
    private double lowBloodPressure;

    @Override
    public Serializable pkVal() {
        return this.healthyId;
    }
}
