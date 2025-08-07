package com.biology.domain.manage.shiJuan.command;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import lombok.Data;

@Data
public class AddShiJuanCommand {

    private String type;

    private String name;

    private Long sort;

}
