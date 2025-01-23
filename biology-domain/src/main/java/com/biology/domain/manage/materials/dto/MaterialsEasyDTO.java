package com.biology.domain.manage.materials.dto;

import java.util.List;

import org.springframework.beans.BeanUtils;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.biology.common.annotation.ExcelColumn;
import com.biology.domain.manage.materials.db.MaterialsEntity;
import com.biology.domain.manage.materials.db.MaterialsValueEntity;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class MaterialsEasyDTO {
    @ApiModelProperty("物料档案ID")
    @Schema(description = "物料档案ID")
    private Long materialsId;

    @ApiModelProperty("物料名称")
    @Schema(description = "物料名称")
    private String name;

    @ApiModelProperty("物料编号")
    @Schema(description = "物料编号")
    private String code;

    @ApiModelProperty("物料规格")
    @Schema(description = "物料规格")
    private String specification;

    @ApiModelProperty("物料类型")
    @Schema(description = "物料类型")
    private String type;

    @ApiModelProperty("库存量")
    @Schema(description = "库存量")
    private double stock;

    @ApiModelProperty("物料标签")
    @Schema(description = "物料标签")
    private String tag;

    @ApiModelProperty("当前入库量")
    @ExcelColumn(name = "当前入库量")
    private double lastStock;

    @ApiModelProperty("当前出库量")
    @ExcelColumn(name = "当前出库量")
    private double outStock;

    @ApiModelProperty("批次")
    private String batch;

    @ApiModelProperty("单位")
    @Schema(description = "单位")
    private String unit;

    @Schema(description = "描述")
    private String description;

    public MaterialsEasyDTO(MaterialsEntity entity) {
        if (entity != null) {
            BeanUtils.copyProperties(entity, this);
        }
        loadDescription();
    }

    public void loadDescription() {
        if (getMaterialsId() != null) {
            QueryWrapper<MaterialsValueEntity> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("materials_id", getMaterialsId());
            List<MaterialsValueEntity> values = new MaterialsValueEntity().selectList(queryWrapper);
            if (values != null) {
                for (MaterialsValueEntity value : values) {
                    if (value.getSCondition().equals("小于等于") && getStock() <= value.getValue()) {
                        setDescription(String.format("库存量小于等于%s", value.getValue()));
                        break;
                    }
                    if (value.getSCondition().equals("大于等于") && getStock() >= value.getValue()) {
                        setDescription(String.format("库存量大于等于%s", value.getValue()));
                        break;
                    }
                    if (value.getSCondition().equals("等于") && getStock() == value.getValue()) {
                        setDescription(String.format("库存量等于%s", value.getValue()));
                        break;
                    }
                    if (value.getSCondition().equals("小于") && getStock() < value.getValue()) {
                        setDescription(String.format("库存量小于%s", value.getValue()));
                        break;
                    }
                    if (value.getSCondition().equals("大于") && getStock() > value.getValue()) {
                        setDescription(String.format("库存量大于%s", value.getValue()));
                        break;
                    }
                }
            }
        }
    }
}
