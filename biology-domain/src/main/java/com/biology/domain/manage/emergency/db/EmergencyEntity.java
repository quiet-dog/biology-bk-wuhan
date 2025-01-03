package com.biology.domain.manage.emergency.db;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.biology.common.core.base.BaseEntity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@TableName("manage_emergency")
@ApiModel(value = "EmergencyEntity对象", description = "应急信息表")
public class EmergencyEntity extends BaseEntity<EmergencyEntity> {
    private static final long serialVersionUID = 1L;

    @TableId(value = "emergency_id", type = IdType.AUTO)
    private Long emergencyId;

    @ApiModelProperty("应急编号")
    @TableField(value = "code")
    private String code;

    @ApiModelProperty("应急名称")
    @TableField(value = "title")
    private String title;

    @ApiModelProperty("应急版本号")
    @TableField(value = "version")
    private String version;

    @ApiModelProperty("颁发部门")
    @TableField(value = "dept_id")
    private Long deptId;

    @ApiModelProperty("适用范围")
    @TableField(value = "scope")
    private String scope;

    @ApiModelProperty("风险类型")
    @TableField(value = "risk_type")
    private String riskType;

    @Override
    public Long pkVal() {
        return this.emergencyId;
    }
}
