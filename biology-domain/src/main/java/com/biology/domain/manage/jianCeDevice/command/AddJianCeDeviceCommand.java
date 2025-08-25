package com.biology.domain.manage.jianCeDevice.command;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class AddJianCeDeviceCommand {

    private String deviceSn;

    private String area;

    private String name;
}
