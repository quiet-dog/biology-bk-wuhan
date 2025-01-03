package com.biology.domain.manage.sop.db;

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
@TableName("manage_sop")
@ApiModel(value = "SopEntity对象", description = "Sop信息表")
public class SopEntity extends BaseEntity<SopEntity> {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("sopID")
    @TableId(value = "sop_id", type = IdType.AUTO)
    private Long sopId;

    @ApiModelProperty("SOP名称")
    @TableField(value = "name")
    private String name;

    @ApiModelProperty("sop编号")
    @TableField(value = "code")
    private String code;

    @ApiModelProperty("sop范围")
    @TableField(value = "scope")
    private String scope;

    @Override
    public Serializable pkVal() {
        return this.sopId;
    }
}
