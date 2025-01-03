package com.biology.domain.manage.task.db;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.biology.common.core.base.BaseEntity;

import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@TableName("manage_materials_task")
@ApiModel(value = "SopEntity对象", description = "Sop信息表")
public class MaterialsTaskEntity extends BaseEntity<MaterialsTaskEntity> {

    @TableId(value = "materials_task_id", type = IdType.AUTO)
    private Long materialsTaskId;

    @TableField("materials_id")
    private Long materialsId;

    @Schema(description = "库存")
    @TableField("stock")
    private double stock;

    // @Schema(description = "统计类型")
    // private String type;

    @Override
    public Long pkVal() {
        return this.materialsTaskId;
    }
}
