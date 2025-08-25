package com.biology.domain.manage.smAlarm.db;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.biology.common.core.base.BaseEntity;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@TableName("manage_sm_alarm")
@ApiModel(value = "ReceiveEntity对象", description = "心理试卷表")
public class SmAlarmEntity extends BaseEntity<SmAlarmEntity> {
    private static final long serialVersionUID = 1L;

    @TableId(value = "sm_alarm_id", type = IdType.AUTO)
    private Long smAlarmId;

    @TableField(value = "type")
    private String type;

    @TableField(value = "description")
    private String description;

    @TableField(value = "device_sn")
    private String deviceSn;

    @Override
    public Serializable pkVal() {
        return this.smAlarmId;
    }
}
