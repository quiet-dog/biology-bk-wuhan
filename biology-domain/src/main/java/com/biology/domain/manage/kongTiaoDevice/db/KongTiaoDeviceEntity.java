package com.biology.domain.manage.kongTiaoDevice.db;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.biology.common.core.base.BaseEntity;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@TableName(value = "manage_kong_tiao_device", autoResultMap = true)
@ApiModel(value = "KongTiaoDeviceEntity对象", description = "空调设备表")
public class KongTiaoDeviceEntity extends BaseEntity<KongTiaoDeviceEntity> {
    private static final long serialVersionUID = 1L;

    @TableId(value = "kong_tiao_device_id", type = IdType.AUTO)
    private Long kongTiaoDeviceId;

    @TableField(value = "device_sn")
    private String deviceSn;

    @TableField(value = "name")
    private String name;

    @TableField(value = "area")
    private String area;

    @TableField(value = "last_time")
    private Long lastTime;

    @TableField(value = "extend", typeHandler = JacksonTypeHandler.class)
    private KongTiaoDeviceLabel extend;

    @Override
    public Serializable pkVal() {
        return this.kongTiaoDeviceId;
    }
}
