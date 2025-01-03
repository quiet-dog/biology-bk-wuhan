package com.biology.domain.manage.alarmlevel.dto;

import org.springframework.beans.BeanUtils;

import com.baomidou.mybatisplus.annotation.TableField;
import com.biology.domain.manage.alarmlevel.db.AlarmlevelDetailEntity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class AlarmlevelDetailDTO {

    @Schema(description = "报警级别Id")
    private Long alarmlevelId;

    @Schema(description = "环境档案Id")
    private Long environmentId;

    @Schema(description = "最小值")
    private double min;

    @Schema(description = "最大值")
    private double max;

    // 级别
    @Schema(description = "级别")
    private String level;

    @Schema(description = "单位")
    private String unit;

    public AlarmlevelDetailDTO(AlarmlevelDetailEntity entity) {
        if (entity != null) {
            BeanUtils.copyProperties(entity, this);
        }
    }
}
