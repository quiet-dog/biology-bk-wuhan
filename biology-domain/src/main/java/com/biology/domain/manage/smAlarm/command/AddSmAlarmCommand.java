package com.biology.domain.manage.smAlarm.command;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import lombok.Data;

@Data
public class AddSmAlarmCommand {

    private String type;

    private String description;

    private String deviceSn;
}
