package com.biology.domain.manage.animation.dto;

import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.biology.domain.manage.animation.db.AnimationEntity;
import com.biology.domain.manage.craftarchive.db.CraftArchiveEntity;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class AnimationDTO {

    @Schema(description = "动画id")
    private Long animationId; // 动画id

    @Schema(description = "动画名称")
    private String name; // 动画名称

    @ApiModelProperty("动画描述")
    private String description; // 动画描述

    @ApiModelProperty("动画类型")
    private String type; // 动画类型

    @ApiModelProperty("键值")
    private String key;

    @ApiModelProperty("工艺节点描述")
    private String craftArchiveName;

    public AnimationDTO(AnimationEntity entity) {
        if (entity != null) {
            this.animationId = entity.getAnimationId();
            this.name = entity.getName();
            this.description = entity.getDescription();
            this.type = entity.getType();
            this.key = entity.getKey();

            loadCraftName();
        }
    }

    public void loadCraftName() {
        if (this.getAnimationId() != null && this.getAnimationId() > 0) {
            this.craftArchiveName = ""; // Placeholder for actual craft name retrieval logic
            QueryWrapper<CraftArchiveEntity> wrapper = new QueryWrapper<>();
            wrapper.inSql("craft_archive_id",
                    "select craft_archive_id from manage_animation_craft_archive where animation_id = "
                            + this.getAnimationId());
            List<CraftArchiveEntity> craftProcesses = new CraftArchiveEntity()
                    .selectList(wrapper);
            if (craftProcesses != null && !craftProcesses.isEmpty()) {
                StringBuilder craftNames = new StringBuilder();
                for (CraftArchiveEntity craftProcess : craftProcesses) {
                    if (craftNames.length() > 0) {
                        craftNames.append(", ");
                    }
                    craftNames.append(craftProcess.getCraftArchiveName());
                }
                this.craftArchiveName = craftNames.toString();
            }
        }
    }
}
