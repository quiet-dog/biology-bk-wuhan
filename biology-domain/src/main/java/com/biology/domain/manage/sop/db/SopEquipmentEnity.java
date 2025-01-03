package com.biology.domain.manage.sop.db;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@TableName("manage_sop_equipment")
@ApiModel(value = "SopEntity对象", description = "Sop信息表")
public class SopEquipmentEnity extends Model<SopEquipmentEnity> {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("sopID")
    @TableId(value = "sop_id", type = IdType.AUTO)
    private Long sopId;

    @TableField(value = "equipment_id")
    private Long equipmentId;

    @Override
    public Long pkVal() {
        return this.sopId;
    }
}
