package com.biology.domain.manage.nongDuDevice.db;

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
@TableName(value = "manage_nong_du_device")
@ApiModel(value = "ReceiveEntity对象", description = "浓度设备表")
public class NongDuDeviceEntity extends BaseEntity<NongDuDeviceEntity> {
    private static final long serialVersionUID = 1L;

    @TableId(value = "nong_du_device_id", type = IdType.AUTO)
    private Long nongDuDeviceId;

    @TableField(value = "device_sn")
    private String deviceSn;

    @TableField(value = "name")
    private String name;

    @TableField(value = "area")
    private String area;

    @TableField(value = "last_time")
    private Long lastTime;

    @Override
    public Serializable pkVal() {
        return this.nongDuDeviceId;
    }
}
