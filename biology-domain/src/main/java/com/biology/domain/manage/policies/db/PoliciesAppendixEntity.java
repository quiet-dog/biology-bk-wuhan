package com.biology.domain.manage.policies.db;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@TableName("manage_policies_appendix")
@ApiModel(value = "PoliciesAppendixEntity对象", description = "政策法规附件表")
public class PoliciesAppendixEntity extends Model<PoliciesAppendixEntity> {

    @TableId(value = "policies_id", type = IdType.AUTO)
    private Long policiesId;

    @TableField("path")
    private String path;

    @Override
    public Serializable pkVal() {
        return this.policiesId;
    }
}
