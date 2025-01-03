package com.biology.domain.manage.environment.db;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.biology.common.core.base.BaseEntity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@TableName("manage_environment")
@ApiModel(value = "EnvironmentEntity对象", description = "环境档案表")
public class EnvironmentEntity extends BaseEntity<EnvironmentEntity> {
    private static final long serialVersionUID = 1L;

    @TableId(value = "environment_id", type = IdType.AUTO)
    private Long environmentId;

    @TableField(value = "description")
    private String description;

    // 监测点位
    @TableField(value = "monitoring_point")
    private String monitoringPoint;

    // 环境指标
    // @TableField(value = "environment_index")
    // private String environmentIndex;

    // 位号
    @TableField(value = "tag")
    private String tag;

    // 类型
    @TableField(value = "type")
    private String type;

    // 范围
    @TableField(value = "scope")
    private String scope;

    // 信号
    @TableField(value = "e_signal")
    private String eSignal;

    // 设备仪表供应商
    @TableField(value = "supplier")
    private String supplier;

    // 设备仪表型号
    @TableField(value = "model")
    private String model;

    @ApiModelProperty("PLC地址")
    @TableField("plc_address")
    private String plcAddress;

    @ApiModelProperty("区域")
    @TableField("e_area")
    private String eArea;

    // 数值
    @TableField(value = "value")
    private double value;

    @Schema(description = "单位")
    @TableField(value = "unit")
    private String unit;

    @Schema(description = "监测指标")
    @TableField(value = "unit_name")
    private String unitName;

    @Override
    public Long pkVal() {
        return this.environmentId;
    }
}
