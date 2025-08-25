package com.biology.domain.manage.nongDuDevice.command;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import lombok.Data;

@Data
public class AddNongDuDeviceCommand {

    private String deviceSn;

    private String name;

    private String area;

    private Long lastTime;
}
