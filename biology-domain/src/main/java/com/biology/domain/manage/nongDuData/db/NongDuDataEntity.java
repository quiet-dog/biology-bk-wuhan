package com.biology.domain.manage.nongDuData.db;

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
@TableName(value = "manage_nong_du_data")
@ApiModel(value = "ReceiveEntity对象", description = "浓度数据表")
public class NongDuDataEntity extends BaseEntity<NongDuDataEntity> {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("nong_du_data_id")
    @TableId(value = "nong_du_data_id", type = IdType.AUTO)
    private Long nongDuDataId;

    @TableField(value = "device_sn")
    private String deviceSn;

    @TableField(value = "particle_concentration")
    private Double particleConcentration;

    @TableField(value = "biological_concentration")
    private Double biologicalConcentration;

    @TableField(value = "alarm")
    private Integer alarm;

    @Override
    public Serializable pkVal() {
        return this.nongDuDataId;
    }
}
