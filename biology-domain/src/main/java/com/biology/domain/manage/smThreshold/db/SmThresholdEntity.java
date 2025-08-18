package com.biology.domain.manage.smThreshold.db;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@TableName(value = "manage_sm_threshold", autoResultMap = true)
@ApiModel(value = "SmThresholdEntity对象", description = "生命设备阈值")
public class SmThresholdEntity extends Model<SmThresholdEntity> {

    @TableId(value = "sm_device_id", type = IdType.AUTO)
    private Long smDeviceId;

    @TableField(value = "min")
    private double min;

    @TableField(value = "max")
    private double max;

    @TableField(value = "`type`")
    private String type;

    // 级别
    @TableField("level")
    private String level;

    @Override
    public Long pkVal() {
        return this.smDeviceId;
    }
}
