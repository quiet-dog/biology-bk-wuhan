package com.biology.domain.manage.healthyMoni.command;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class AddHealthyMoniCommand {

    private Long personnelId;

    private String fieldType;

    private Double min;

    private Double max;

    private Integer pushFrequency;

    private Boolean isPush;
}
