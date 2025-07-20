package com.biology.domain.manage.materials.command;

import java.util.List;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.biology.common.annotation.ExcelColumn;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class AddMaterialsCommand {

    @Schema(description = "物料名称")
    @ExcelColumn(name = "物料名称")
    private String name;

    @Schema(description = "物料编号")
    @ExcelColumn(name = "物料编号")
    private String code;

    @Schema(description = "物料规格")
    @ExcelColumn(name = "物料规格")
    private String specification;

    // @Schema(description = "物料类型")
    // @ExcelColumn(name = "物料类型")
    // private String scope;

    @Schema(description = "物料类型")
    @ExcelColumn(name = "物料类型")
    private String type;

    @Schema(description = "入库量")
    @ExcelColumn(name = "入库量")
    private double stock;

    @Schema(description = "物料标签")
    @ExcelColumn(name = "物料标签")
    private String tag;

    @ApiModelProperty("当前入库量")
    // @ExcelColumn(name = "当前入库量")
    private double lastStock;

    @ApiModelProperty("批次")
    @ExcelColumn(name = "批次")
    private String batch;

    // @ApiModelProperty("当前出库量")
    // @TableField(value = "out_stock")
    // private double outStock;

    @Schema(description = "单位")
    @ExcelColumn(name = "单位")
    private String unit;

    @ApiModelProperty("型号")
    @ExcelColumn(name = "型号")
    private String model;

    @ApiModelProperty("供应商")
    @ExcelColumn(name = "供应商")
    private String supplier;

    @ApiModelProperty("标签颜色")
    private String color;

    @ApiModelProperty("标签颜色描述")
    private String colorDescription;

    @Schema(description = "阈值设置")
    List<ValueCommand> values;

    @Schema(description = "总容量")
    private double total;

    @Schema(description = "技术规格")
    private String technicalSpecification;

    @Schema(description = "性能参数")
    private String performanceParameters;
}
