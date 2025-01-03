package com.biology.domain.manage.materials.dto;

import org.springframework.beans.BeanUtils;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.biology.domain.manage.materials.db.MaterialsValueEntity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ValueDTO {

    // @ApiModelProperty("物料阈值ID")
    // private Long materialsValueId;

    @ApiModelProperty("物料ID")
    private Long materialsId;

    @ApiModelProperty("报警级别")
    private String level;

    @ApiModelProperty("阈值条件")
    private String sCondition;

    @ApiModelProperty("指标数值")
    private double value;

    public ValueDTO(MaterialsValueEntity entity) {
        if (entity != null) {
            BeanUtils.copyProperties(entity, this);
        }
    }

}
