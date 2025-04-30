package com.biology.domain.manage.moni.db;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.biology.common.core.base.BaseEntity;
import com.biology.domain.manage.materials.db.MaterialsEntity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@TableName("manage_moni")
@ApiModel(value = "模拟数据对象", description = "模拟数据")
public class MoniEntity extends BaseEntity<MaterialsEntity> {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("模拟ID")
    @TableId(value = "moni_id", type = IdType.AUTO)
    private Long moniId;

    @ApiModelProperty("描述")
    @TableField(value = "description")
    private String description;

    @ApiModelProperty("阈值Id")
    @TableField(value = "threshold_id")
    private Long thresholdId;

    @ApiModelProperty("推送类型")
    @TableField(value = "push_type")
    private String pushType;

    @ApiModelProperty("推送频率")
    @TableField(value = "push_frequency")
    private Integer pushFrequency;

    @ApiModelProperty("最小值")
    @TableField(value = "min")
    private Double min;

    @ApiModelProperty("最大值")
    @TableField(value = "max")
    private Double max;

    @ApiModelProperty("是否推送")
    @TableField(value = "is_push")
    private Boolean isPush;

    @Override
    public Serializable pkVal() {
        return this.moniId;
    }
}
