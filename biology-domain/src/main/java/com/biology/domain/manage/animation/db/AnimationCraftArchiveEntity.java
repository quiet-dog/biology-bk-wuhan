package com.biology.domain.manage.animation.db;

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
@TableName("manage_animation_craft_archive")
@ApiModel(value = "AnimationEntity对象", description = "动画实体类")
public class AnimationCraftArchiveEntity extends Model<AnimationCraftArchiveEntity> {
    private static final long serialVersionUID = 1L;

    @TableId(value = "animation_id", type = IdType.AUTO)
    private Long animationId;

    @TableField(value = "craft_archive_id")
    private Long craftArchiveId;

    @Override
    public Long pkVal() {
        return this.animationId;
    }
}
