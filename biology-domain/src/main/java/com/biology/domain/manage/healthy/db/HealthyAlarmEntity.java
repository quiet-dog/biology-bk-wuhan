package com.biology.domain.manage.healthy.db;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.biology.common.core.base.BaseEntity;

import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@TableName("manage_personnel_healthy_alarm")
@ApiModel(value = "HealthyAlarmEntity对象", description = "人员健康信息报警表")
public class HealthyAlarmEntity extends BaseEntity<HealthyAlarmEntity> {
    private static final long serialVersionUID = 1L;

    @Schema(description = "健康报警Id")
    @TableId(value = "healthy_alarm_id", type = IdType.AUTO)
    private Long healthyAlarmId;

    @Schema(description = "健康数据Id")
    @TableField("healthy_id")
    private Long healthyId;

    @Schema(description = "报警类型")
    @TableField("type")
    private String type;

    @Override
    public Long pkVal() {
        return this.healthyAlarmId;
    }

}
