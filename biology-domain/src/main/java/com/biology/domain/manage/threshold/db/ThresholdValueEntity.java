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
@TableName("manage_threshold_value")
@ApiModel(value = "ThresholdValueEntity对象", description = "阈值表")
public class ThresholdValueEntity extends Model<ThresholdValueEntity> {
    private static final long serialVersionUID = 1L;

    @TableId(value = "threshold_id", type = IdType.AUTO)
    private Long thresholdId;

    @TableField(value = "min")
    private double min;

    @TableField(value = "max")
    private double max;

    // 级别
    @TableField("level")
    private String level;

    @Override
    public Long pkVal() {
        return this.thresholdId;
    }
}
