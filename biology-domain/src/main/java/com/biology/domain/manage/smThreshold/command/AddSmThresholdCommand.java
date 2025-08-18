package com.biology.domain.manage.smThreshold.command;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import lombok.Data;

@Data
public class AddSmThresholdCommand {
    private Long smDeviceId;

    private double min;

    private double max;

    private String type;

    // 级别
    private String level;
}
