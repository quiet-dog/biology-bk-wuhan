package com.biology.domain.manage.threshold.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import lombok.Data;

@Data
public class ThresholdValueDTO {
    @TableId(value = "threshold_id", type = IdType.AUTO)
    private Long thresholdId;

    @TableField(value = "min")
    private String min;

    @TableField(value = "max")
    private String max;

    // 级别
    @TableField("level")
    private String level;
}
