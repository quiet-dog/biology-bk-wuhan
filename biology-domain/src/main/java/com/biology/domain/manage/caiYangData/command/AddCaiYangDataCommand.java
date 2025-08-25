package com.biology.domain.manage.caiYangData.command;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import lombok.Data;

@Data
public class AddCaiYangDataCommand {

    private String deviceSn;

    private Long workTime;

    private Long endTime;
}
