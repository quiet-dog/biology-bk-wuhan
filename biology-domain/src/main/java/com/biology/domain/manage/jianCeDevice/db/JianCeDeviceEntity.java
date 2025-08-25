package com.biology.domain.manage.jianCeDevice.db;

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
@TableName("manage_jian_ce_device")
@ApiModel(value = "HealthyEntity对象", description = "人员健康信息表")
public class JianCeDeviceEntity extends BaseEntity<JianCeDeviceEntity> {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("健康Id")
    @TableId(value = "jian_ce_device_id", type = IdType.AUTO)
    private Long jianCeDeviceId;

    @TableField(value = "device_sn")
    private String deviceSn;

    @TableField(value = "area")
    private String area;

    @TableField(value = "last_time")
    private Long lastTime;

    @TableField(value = "name")
    private String name;

    @Override
    public Serializable pkVal() {
        return this.jianCeDeviceId;
    }
}
