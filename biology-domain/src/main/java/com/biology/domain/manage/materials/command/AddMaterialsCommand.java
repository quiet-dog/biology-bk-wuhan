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

    @Schema(description = "库存量")
    @ExcelColumn(name = "库存量")
    private double stock;

    @Schema(description = "单位")
    @ExcelColumn(name = "单位")
    private String unit;

    @Schema(description = "阈值设置")
    List<ValueCommand> values;
}
