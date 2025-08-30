package com.biology.domain.manage.nongDuData.command;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class AddNongDuDataCommand {

    private String deviceSn;

    private Double particleConcentration;

    private Double biologicalConcentration;

    private Integer alarm;

    private Integer workingStatus;
}
