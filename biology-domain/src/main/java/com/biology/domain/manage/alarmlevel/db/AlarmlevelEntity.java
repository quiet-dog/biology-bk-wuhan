package com.biology.domain.manage.alarmlevel.db;

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
@TableName("manage_alarmlevel")
@ApiModel(value = "AlarmlevelEntity对象", description = "报警级别设置表")
public class AlarmlevelEntity extends BaseEntity<AlarmlevelEntity> {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("alarmlevel_id")
    @TableId(value = "alarmlevel_id", type = IdType.AUTO)
    private Long alarmlevelId;

    @ApiModelProperty("环境档案Id")
    @TableField("environment_id")
    private String environmentId;

    @ApiModelProperty("PLC地址")
    @TableField("plc_address")
    private String plcAddress;

    @ApiModelProperty("启用/禁用")
    @TableField("enable")
    private boolean enable;

    @ApiModelProperty("单位")
    @TableField("unit")
    private String unit;

    @Override
    public Serializable pkVal() {
        return this.alarmlevelId;
    }
}
