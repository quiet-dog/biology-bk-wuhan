package com.biology.domain.manage.alarm.db;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.biology.common.core.base.BaseEntity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@TableName("manage_alarm")
@ApiModel(value = "AlarmEntity对象", description = "物料报警表")
public class AlarmEntity extends BaseEntity<AlarmEntity> {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("alarm_id")
    @TableId(value = "alarm_id", type = IdType.AUTO)
    private Long alarmId;

    @ApiModelProperty("物料Id")
    @TableField(value = "materials_id")
    private Long materialsId;

    @ApiModelProperty("报警时候的库存")
    @TableField(value = "stock")
    private double stock;

    @ApiModelProperty("报警级别")
    @TableField(value = "level")
    private String level;

    @Override
    public Serializable pkVal() {
        return this.alarmId;
    }
}
