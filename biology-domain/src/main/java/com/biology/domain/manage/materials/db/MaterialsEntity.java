package com.biology.domain.manage.materials.db;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.biology.common.core.base.BaseEntity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@TableName("manage_materials")
@ApiModel(value = "MaterialsEntity对象", description = "物料档案")
public class MaterialsEntity extends BaseEntity<MaterialsEntity> {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("物料档案ID")
    @TableId(value = "materials_id", type = IdType.AUTO)
    private Long materialsId;

    @ApiModelProperty("物料名称")
    @TableField(value = "name")
    private String name;

    @ApiModelProperty("物料编号")
    @TableField(value = "code")
    private String code;

    @ApiModelProperty("物料类型")
    @TableField(value = "materials_type")
    private String type;

    @ApiModelProperty("物料规格")
    @TableField(value = "specification")
    private String specification;

    @ApiModelProperty("库存量")
    @TableField(value = "stock")
    private double stock;

    @ApiModelProperty("单位")
    @TableField(value = "unit")
    private String unit;

    @Override
    public Serializable pkVal() {
        return this.materialsId;
    }

}
