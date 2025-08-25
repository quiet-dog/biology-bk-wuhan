package com.biology.domain.manage.xsData.db;

import java.io.Serializable;
import java.util.List;

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
@TableName(value = "manage_xs_data")
@ApiModel(value = "ReceiveEntity对象", description = "生命设备表")
public class XsDataEntity extends BaseEntity<XsDataEntity> {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("xs_data_id")
    @TableId(value = "xs_data_id", type = IdType.AUTO)
    private Long xsDataId;

    // @TableField(value = "xs_device_id")
    // private Long xsDeviceId;

    @TableField(value = "device_sn")
    private String deviceSn;

    @TableField(value = "end_time")
    private Long endTime;

    @Override
    public Serializable pkVal() {
        return this.xsDataId;
    }
}
