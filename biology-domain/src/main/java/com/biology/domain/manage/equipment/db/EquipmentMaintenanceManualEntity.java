package com.biology.domain.manage.equipment.db;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.fasterxml.jackson.databind.JsonNode;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@TableName(value = "manage_equipment_maintenance_manual", autoResultMap = true)
@ApiModel(value = "EquipmentMaintenanceManualEntity", description = "维修手册")
public class EquipmentMaintenanceManualEntity extends Model<EquipmentMaintenanceManualEntity> {

    @ApiModelProperty("手册ID")
    @TableId(value = "manual_id", type = IdType.AUTO)
    private Long manualId;

    @ApiModelProperty("设备ID")
    @TableField("equipment_id")
    private Long equipmentId;

    @ApiModelProperty("手册版本")
    @TableField("manual_version")
    private String manualVersion;

    @ApiModelProperty("手册编号")
    @TableField("manual_code")
    private String manualCode;

    @ApiModelProperty("是否启用")
    @TableField("is_enabled")
    private Boolean isEnabled;

    @ApiModelProperty("手册文件路径")
    @TableField(value = "manual_file_path", typeHandler = JacksonTypeHandler.class)
    private JsonNode manualFilePath;

    @ApiModelProperty("创建时间")
    @TableField("create_time")
    private Date createTime;

    @ApiModelProperty("更新时间")
    @TableField("update_time")
    private Date updateTime;

    @ApiModelProperty("删除标志")
    @TableField("deleted")
    private Boolean deleted;

    @Override
    public Long pkVal() {
        return this.manualId;
    }
}