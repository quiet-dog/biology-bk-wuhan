package com.biology.domain.manage.policies.db;

import java.io.Serializable;
import java.util.Date;

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
@TableName("manage_policies")
@ApiModel(value = "PoliciesEntity对象", description = "政策法规表")
public class PoliciesEntity extends BaseEntity<PoliciesEntity> {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("policies_id")
    @TableId(value = "policies_id", type = IdType.AUTO)
    private Long policiesId;

    @ApiModelProperty("policies_code")
    @TableField("policies_code")
    private String policiesCode;

    @ApiModelProperty("policies_name")
    @TableField("policies_name")
    private String policiesName;

    @TableField("type")
    private String type;

    // @ApiModelProperty("release_date")
    // @TableField("release_date")
    // private Date releaseDate;

    @Override
    public Serializable pkVal() {
        return this.policiesId;
    }

}
