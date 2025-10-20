package com.biology.domain.manage.healthyMoni.db;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.biology.common.core.base.BaseEntity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@TableName("manage_moni_personnel_healthy")
@ApiModel(value = "HealthyEntity对象", description = "人员健康模拟表")
public class HealthyMoniEntity extends BaseEntity<HealthyMoniEntity> {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("健康模拟")
    @TableId(value = "healthy_moni_id", type = IdType.AUTO)
    private Long healthyMoniId;

    @ApiModelProperty("人员Id")
    @TableField("personnel_id")
    private Long personnelId;

    @TableField("field_type")
    private String fieldType;

    @TableField("min")
    private Double min;

    @TableField("max")
    private Double max;

    @ApiModelProperty("推送频率")
    @TableField(value = "push_frequency")
    private Integer pushFrequency;

    @ApiModelProperty("是否推送")
    @TableField(value = "is_push")
    private Boolean isPush;

    @Override
    public Serializable pkVal() {
        return this.healthyMoniId;
    }
}
