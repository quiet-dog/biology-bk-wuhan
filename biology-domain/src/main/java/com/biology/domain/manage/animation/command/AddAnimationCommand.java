package com.biology.domain.manage.animation.command;

import com.baomidou.mybatisplus.annotation.TableField;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class AddAnimationCommand {
    @Schema(description = "动画名称")
    private String name; // 动画名称

    @ApiModelProperty("动画描述")
    private String description; // 动画描述

    @ApiModelProperty("动画类型")
    private String type; // 动画类型

    @ApiModelProperty("键值")
    private String key;
}
