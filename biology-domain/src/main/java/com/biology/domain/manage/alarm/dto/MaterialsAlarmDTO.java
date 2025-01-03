package com.biology.domain.manage.alarm.dto;

import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;

import com.biology.domain.manage.materials.db.MaterialsEntity;
import com.biology.domain.manage.materials.db.MaterialsValueEntity;
import com.biology.domain.manage.materials.dto.ValueDTO;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class MaterialsAlarmDTO {
    @ApiModelProperty("物料档案ID")
    @Schema(description = "物料档案ID")
    private Long materialsId;

    @ApiModelProperty("物料名称")
    @Schema(description = "物料名称")
    private String name;

    @ApiModelProperty("物料编号")
    @Schema(description = "物料编号")
    private String code;

    @ApiModelProperty("物料类型")
    @Schema(description = "物料类型")
    private String type;

    @ApiModelProperty("库存量")
    @Schema(description = "库存量")
    private double stock;

    @ApiModelProperty("单位")
    @Schema(description = "单位")
    private String unit;

    @Schema(description = "创建时间")
    private Date createTime;

    @Schema(description = "更新时间")
    private Date updateTime;

    @Schema(description = "阈值报警")
    private MaterialsValueEntity value;

    public MaterialsAlarmDTO(MaterialsEntity entity) {
        if (entity != null) {
            BeanUtils.copyProperties(entity, this);
        }
    }
}
