package com.biology.domain.manage.materials.dto;

import java.util.ArrayList;
import java.util.Date;
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
public class MaterialsDTO {

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

    @Schema(description = "创建时间")
    private Date createTime;

    @Schema(description = "更新时间")
    private Date updateTime;

    @Schema(description = "阈值信息")
    private List<ValueDTO> values;

    public MaterialsDTO(MaterialsEntity entity) {
        if (entity != null) {
            BeanUtils.copyProperties(entity, this);
            MaterialsValueEntity valueEntity = new MaterialsValueEntity();
            QueryWrapper<MaterialsValueEntity> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("materials_id", entity.getMaterialsId());
            List<MaterialsValueEntity> valueList = valueEntity.selectList(queryWrapper);
            List<ValueDTO> findValues = new ArrayList<>();
            if (valueList != null) {
                for (MaterialsValueEntity value : valueList) {
                    findValues.add(new ValueDTO(value));
                }
            }
            this.values = findValues;
        }
    }

}
