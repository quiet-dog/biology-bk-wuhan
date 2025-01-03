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
@TableName("manage_threshold_sop")
@ApiModel(value = "ThresholdSopEntity对象", description = "sop阈值表")
public class ThresholdSopEntity extends Model<ThresholdSopEntity> {
    private static final long serialVersionUID = 1L;

    @TableId(value = "threshold_id", type = IdType.AUTO)
    private Long thresholdId;

    @TableField(value = "sop_id")
    private Long sopId;

    @Override
    public Long pkVal() {
        return this.thresholdId;
    }
}
