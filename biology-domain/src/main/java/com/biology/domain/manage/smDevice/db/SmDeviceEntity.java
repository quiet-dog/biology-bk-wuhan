package com.biology.domain.manage.smDevice.db;

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
@TableName("manage_sm_device")
@ApiModel(value = "ReceiveEntity对象", description = "生命设备表")
public class SmDeviceEntity extends BaseEntity<SmDeviceEntity> {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("sm_device_id")
    @TableId(value = "sm_device_id", type = IdType.AUTO)
    private Long smDeviceId;

    @TableField(value = "device_sn")
    private String deviceSn;

    @TableField(value = "name")
    private String name;

    @TableField(value = "personnel_id")
    private Long personnelId;

    @TableField(value = "area")
    private String area;

    @Override
    public Serializable pkVal() {
        return this.smDeviceId;
    }
}
