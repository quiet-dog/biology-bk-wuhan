package com.biology.domain.manage.moni.db;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@TableName("manage_moni_threshold")
@ApiModel(value = "模拟阈值对象", description = "模拟阈值")
public class MoniThresholdEntity extends Model<MoniThresholdEntity> {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("模拟ID")
    @TableId(value = "moni_id", type = IdType.AUTO)
    private Long moniId;

    @ApiModelProperty("阈值Id")
    @TableField(value = "threshold_id")
    private Long thresholdId;

    @ApiModelProperty("设备Id")
    @TableField(value = "equipment_id")
    private Long equipmentId;

    @ApiModelProperty("环境ID")
    @TableField(value = "environment_id")
    private Long environmentId;

    @Override
    public Serializable pkVal() {
        return this.moniId;
    }
}
