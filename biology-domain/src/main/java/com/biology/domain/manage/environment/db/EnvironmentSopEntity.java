package com.biology.domain.manage.environment.db;

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
@TableName("manage_environment_sop")
@ApiModel(value = "EnvironmentSopEntity对象", description = "环境档案sop表")
public class EnvironmentSopEntity extends Model<EnvironmentSopEntity> {
    private static final long serialVersionUID = 1L;

    @TableId(value = "environment_id", type = IdType.AUTO)
    private Long environmentId;

    @TableField(value = "sop_id")
    private Long sopId;

    @Override
    public Long pkVal() {
        return this.environmentId;
    }
}
