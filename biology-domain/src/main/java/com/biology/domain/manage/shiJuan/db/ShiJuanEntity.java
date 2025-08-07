package com.biology.domain.manage.shiJuan.db;

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
@TableName("manage_xl_shijuan")
@ApiModel(value = "ReceiveEntity对象", description = "心理试卷表")
public class ShiJuanEntity extends BaseEntity<ShiJuanEntity> {
    private static final long serialVersionUID = 1L;

    @TableId(value = "xl_shi_juan_id", type = IdType.AUTO)
    private Long xlShiJuanId;

    @TableField(value = "type")
    private String type;

    @TableField(value = "name")
    private String name;

    @TableField(value = "sort")
    private Long sort;

    @Override
    public Serializable pkVal() {
        return this.xlShiJuanId;
    }
}
