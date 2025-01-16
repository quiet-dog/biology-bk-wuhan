package com.biology.domain.manage.materials.command;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class AddWarehouseCommand {

    @ApiModelProperty("物料档案ID")
    @TableField(value = "materials_id")
    private Long materialsId;

    // 入库数量
    @ApiModelProperty("入库数量")
    private double stock;

    // 批次
    @ApiModelProperty("批次")
    private String batch;

    @ApiModelProperty("剩余库存")
    private double remainStock;
}
