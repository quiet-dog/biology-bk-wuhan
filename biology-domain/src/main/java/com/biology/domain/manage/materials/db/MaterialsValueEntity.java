package com.biology.domain.manage.materials.db;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.biology.common.core.base.BaseEntity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@TableName("manage_materials_value")
@ApiModel(value = " MaterialsValue对象", description = "物料阈值")
public class MaterialsValueEntity extends Model<MaterialsValueEntity> {
    private static final long serialVersionUID = 1L;

    // @ApiModelProperty("物料阈值ID")
    // @TableId(value = "materials_value_id", type = IdType.AUTO)
    // private Long materialsValueId;

    // @ApiModelProperty("物料ID")
    // @TableField(value = "materials_id")
    @TableId(value = "materials_id", type = IdType.AUTO)
    private Long materialsId;

    @ApiModelProperty("报警级别")
    @TableField(value = "level")
    private String level;

    @ApiModelProperty("阈值条件")
    @TableField(value = "s_condition")
    private String sCondition;

    @ApiModelProperty("指标数值")
    @TableField(value = "value")
    private double value;

    @Override
    public Serializable pkVal() {
        return this.materialsId;
    }
}
