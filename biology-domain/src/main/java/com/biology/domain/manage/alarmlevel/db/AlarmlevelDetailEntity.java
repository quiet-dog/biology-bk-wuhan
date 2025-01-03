package com.biology.domain.manage.alarmlevel.db;

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
@TableName("manage_alarmlevel_detail")
@ApiModel(value = "AlarmlevelEntity对象", description = "环境档案报警级别设置表")
public class AlarmlevelDetailEntity extends Model<AlarmlevelDetailEntity> {
    private static final long serialVersionUID = 1L;

    @TableId(value = "alarmlevel_id", type = IdType.AUTO)
    private Long alarmlevelId;

    @TableField(value = "environment_id")
    private Long environmentId;

    // @TableId(value = "environment_id", type = IdType.AUTO)
    // private Long environmentId;

    @TableField("min")
    private double min;

    @TableField("max")
    private double max;

    // 级别
    @TableField("level")
    private String level;

    @TableField("unit")
    private String unit;

    @Override
    public Long pkVal() {
        return this.alarmlevelId;
    }
}
