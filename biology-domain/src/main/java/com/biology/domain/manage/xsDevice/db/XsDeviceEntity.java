package com.biology.domain.manage.xsDevice.db;

import java.io.Serializable;
import java.util.List;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.biology.common.core.base.BaseEntity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@TableName(value = "manage_xs_device")
@ApiModel(value = "消杀设备对象", description = "消杀设备表")
public class XsDeviceEntity extends BaseEntity<XsDeviceEntity> {
    private static final long serialVersionUID = 1L;

    @TableId(value = "xs_device_id", type = IdType.AUTO)
    private Long xsDeviceId;

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
        return this.xsDeviceId;
    }
}
