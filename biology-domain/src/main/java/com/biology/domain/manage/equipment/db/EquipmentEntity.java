package com.biology.domain.manage.equipment.db;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.biology.common.core.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@TableName(value = "manage_equipment", autoResultMap = true)
@ApiModel(value = "EquipmentEntity对象", description = "设备档案信息表")
public class EquipmentEntity extends BaseEntity<EquipmentEntity> {

    @ApiModelProperty("设备ID")
    @TableId(value = "equipment_id", type = IdType.AUTO)
    private Long equipmentId;

    @ApiModelProperty("所属区域")
    @TableField("area")
    private String area;

    @ApiModelProperty("设备编号")
    @TableField("equipment_code")
    private String equipmentCode;

    @ApiModelProperty("设备名称")
    @TableField("equipment_name")
    private String equipmentName;

    @ApiModelProperty("设备型号")
    @TableField("equipment_type")
    private String equipmentType;

    @ApiModelProperty("生产厂家")
    @TableField("manufacturer")
    private String manufacturer;

    @ApiModelProperty("购置日期")
    @TableField("purchase_date")
    private Date purchaseDate;

    @ApiModelProperty("安装位置")
    @TableField("installation_location")
    private String installationLocation;

    @ApiModelProperty("房间号")
    @TableField("room_number")
    private String roomNumber;

    @ApiModelProperty("需要采集生物安全数据名称")
    @TableField("biosafetydata_name")
    private String biosafetydataName;

    @ApiModelProperty("技术规格")
    @TableField("technical_spec")
    private String technicalSpec;

    @ApiModelProperty("性能参数")
    @TableField("performance_params")
    private String performanceParams;

    @ApiModelProperty("使用状态")
    @TableField("usage_status")
    private String usageStatus;

    // @ApiModelProperty("创建时间")
    // @TableField("create_time")
    // private Date createTime;

    // @ApiModelProperty("更新时间")
    // @TableField("update_time")
    // private Date updateTime;

    // @ApiModelProperty("删除标志")
    // @TableField("deleted")
    // private Boolean deleted;
}
