package com.biology.domain.manage.healthy.command;

import com.baomidou.mybatisplus.annotation.TableField;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class AddHealthyAlarmCommand {
    @Schema(description = "健康数据Id")
    private Long healthyId;

    @Schema(description = "报警类型")
    private String type;
}
