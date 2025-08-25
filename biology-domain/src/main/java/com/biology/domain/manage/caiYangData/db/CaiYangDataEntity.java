package com.biology.domain.manage.caiYangData.db;

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
@TableName(value = "manage_cai_yang_data")
@ApiModel(value = "ReceiveEntity对象", description = "生命设备表")
public class CaiYangDataEntity extends BaseEntity<CaiYangDataEntity> {
    private static final long serialVersionUID = 1L;

    @TableId(value = "cai_yang_data_id", type = IdType.AUTO)
    private Long caiYangDataId;

    @TableField(value = "device_sn")
    private String deviceSn;

    @TableField(value = "work_time")
    private Long workTime;

    @TableField(value = "end_time")
    private Long endTime;

    @Override
    public Serializable pkVal() {
        return this.caiYangDataId;
    }
}
