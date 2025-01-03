package com.biology.domain.manage.emergency.db;

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
@TableName("manage_emergency_equipment")
@ApiModel(value = "EmergencyEquipmentEntity对象", description = "应急信息表")
public class EmergencyEquipmentEntity extends Model<EmergencyEquipmentEntity> {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("EmergencyID")
    @TableId(value = "emergency_id", type = IdType.AUTO)
    private Long emergencyId;

    @TableField(value = "equipment_id")
    private Long equipmentId;

    @Override
    public Long pkVal() {
        return this.emergencyId;
    }
}
