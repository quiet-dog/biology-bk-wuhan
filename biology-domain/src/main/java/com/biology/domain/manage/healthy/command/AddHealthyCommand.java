package com.biology.domain.manage.healthy.command;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class AddHealthyCommand {

    @Schema(defaultValue = "人员Id")
    private Long personnelId;

    @Schema(defaultValue = "体温")
    private double temperature;

    @Schema(defaultValue = "心率")
    private double heartRate;

    @Schema(defaultValue = "收缩压")
    private double highBloodPressure;

    @Schema(defaultValue = "舒张压")
    private double lowBloodPressure;
}
