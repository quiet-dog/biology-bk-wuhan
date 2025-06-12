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

    @ApiModelProperty("当前入库量")
    @TableField(value = "last_stock")
    private double lastStock;

    @ApiModelProperty("当前出库量")
    @TableField(value = "out_stock")
    private double outStock;

    @ApiModelProperty("标签")
    @TableField(value = "tag")
    private String tag;

    @ApiModelProperty("批次")
    @TableField(value = "batch")
    private String batch;

    @ApiModelProperty("单位")
    @TableField(value = "unit")
    private String unit;

    @ApiModelProperty("型号")
    @TableField(value = "model")
    private String model;

    @ApiModelProperty("供应商")
    @TableField(value = "supplier")
    private String supplier;

    @ApiModelProperty("标签颜色")
    @TableField(value = "color")
    private String color;

    @ApiModelProperty("标签颜色描述")
    @TableField(value = "color_description")
    private String colorDescription;

    @ApiModelProperty("总容量")
    @TableField(value = "total")
    private double total;

    @Override
    public Serializable pkVal() {
        return this.materialsId;
    }

}
