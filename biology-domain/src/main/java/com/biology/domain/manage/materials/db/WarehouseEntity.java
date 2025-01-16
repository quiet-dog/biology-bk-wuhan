package com.biology.domain.manage.materials.db;

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
@TableName("manage_warehouse")
@ApiModel(value = "WarehouseEntity对象", description = "入库记录")
public class WarehouseEntity extends BaseEntity<WarehouseEntity> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "warehouse_id", type = IdType.AUTO)
    private Long warehouseId;

    @ApiModelProperty("物料档案ID")
    @TableField(value = "materials_id")
    private Long materialsId;

    // 入库数量
    @ApiModelProperty("入库数量")
    @TableField(value = "stock")
    private double stock;

    // 批次
    @ApiModelProperty("批次")
    @TableField(value = "batch")
    private String batch;

    @ApiModelProperty("剩余库存")
    @TableField(value = "remain_stock")
    private double remainStock;

    @Override
    public Serializable pkVal() {
        return this.materialsId;
    }

}
