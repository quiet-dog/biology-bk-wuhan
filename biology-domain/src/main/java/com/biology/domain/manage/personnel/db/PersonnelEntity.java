package com.biology.domain.manage.personnel.db;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.biology.common.core.base.BaseEntity;
import com.biology.domain.manage.healthy.db.HealthyEntity;
import com.biology.domain.manage.healthy.dto.HealthyDTO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@TableName("manage_personnel")
@ApiModel(value = "PersonnelEntity对象", description = "人员档案")
public class PersonnelEntity extends BaseEntity<PersonnelEntity> {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("personnel_id")
    @TableId(value = "personnel_id", type = IdType.AUTO)
    private Long personnelId;

    @ApiModelProperty("code")
    @TableField(value = "code")
    private String code;

    @ApiModelProperty("name")
    @TableField(value = "name")
    private String name;

    @ApiModelProperty("sex")
    @TableField(value = "sex")
    private String sex;

    // 部门
    @ApiModelProperty("department")
    @TableField(value = "department")
    private String department;
    // 岗位
    @ApiModelProperty("post")
    @TableField(value = "post")
    private String post;
    // 职级
    @ApiModelProperty("p_rank")
    @TableField(value = "p_rank")
    private String pRank;
    // 学历
    @ApiModelProperty("education")
    @TableField(value = "education")
    private String education;
    // 联系方式
    @ApiModelProperty("contact")
    @TableField(value = "contact")
    private String contact;

    @ApiModelProperty("email")
    @TableField(value = "email")
    private String email;

    @ApiModelProperty("身份证号")
    @TableField(value = "card")
    private String card;

    // @ApiModelProperty("工号")
    // @TableField(value = "job_code")
    // private String jobCode;

    @ApiModelProperty("证书")
    @TableField(value = "certificates")
    private String certificates;

    // 入职时间
    @ApiModelProperty("entry_time")
    @TableField(value = "entry_time")
    private Date entryTime;
    // 离职时间
    @ApiModelProperty("leave_time")
    @TableField(value = "leave_time")
    private Date leaveTime;

    @ApiModelProperty("out_id")
    @TableField(value = "out_id")
    private Integer outId;

    @Override
    public Serializable pkVal() {
        return this.personnelId;
    }

}
