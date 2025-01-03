package com.biology.domain.manage.threshold.db;

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
@TableName("manage_threshold_emergency")
@ApiModel(value = "ThresholdEntity对象", description = "应急预案阈值表")
public class ThresholdEmergencyEntity extends Model<ThresholdEmergencyEntity> {
    private static final long serialVersionUID = 1L;

    @TableId(value = "threshold_id", type = IdType.AUTO)
    private Long thresholdId;

    @TableField(value = "emergency_id")
    private Long emergencyId;

    @Override
    public Long pkVal() {
        return this.thresholdId;
    }
}
