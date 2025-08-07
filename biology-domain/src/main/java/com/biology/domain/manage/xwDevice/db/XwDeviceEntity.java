package com.biology.domain.manage.xwDevice.db;

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
@TableName("manage_xw_device")
@ApiModel(value = "ReceiveEntity对象", description = "行为设备表")
public class XwDeviceEntity extends BaseEntity<XwDeviceEntity> {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("xw_device_id")
    @TableId(value = "xw_device_id", type = IdType.AUTO)
    private Long xwDeviceId;

    @TableField(value = "camera_id")
    private String cameraId;

    @TableField(value = "name")
    private String name;

    @TableField(value = "seat_number")
    private String seatNumber;

    @TableField(value = "content")
    private String content;

    @Override
    public Serializable pkVal() {
        return this.xwDeviceId;
    }
}
