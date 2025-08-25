package com.biology.domain.manage.smData.command;

import java.util.List;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class AddSmDataCommand {

    private Long smDeviceId;

    private Double battery;

    private Double co2;

    private Double humility;

    private Double temp;

    private List<Number> huxi;

    private Long sendTime;

    private List<Number> xinDian;

    private Double xinlv;

    private Double xueYang;

    private String tiTai;
}
