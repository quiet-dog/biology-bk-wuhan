package com.biology.domain.manage.animation.db;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.biology.common.core.base.BaseEntity;

import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@TableName("manage_animation")
@ApiModel(value = "AnimationEntity对象", description = "动画实体类")
public class AnimationEntity extends BaseEntity<AnimationEntity> {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("animation_id")
    @TableId(value = "animation_id", type = IdType.AUTO)
    private Long animationId; // 动画ID

    @ApiModelProperty("动画名称")
    @TableField(value = "name")
    private String name; // 动画名称

    @ApiModelProperty("动画描述")
    @TableField(value = "description")
    private String description; // 动画描述

    @ApiModelProperty("动画类型")
    @TableField(value = "type")
    private String type; // 动画类型

    @ApiModelProperty("键值")
    @TableField(value = "`key`")
    private String key;

    @Override
    public Serializable pkVal() {
        return this.animationId;
    }

}
