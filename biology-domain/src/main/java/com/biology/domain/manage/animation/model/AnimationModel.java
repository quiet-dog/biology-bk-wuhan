package com.biology.domain.manage.animation.model;

import org.springframework.beans.BeanUtils;

import com.biology.domain.manage.animation.command.AddAnimationCommand;
import com.biology.domain.manage.animation.command.UpdateAnimationCommand;
import com.biology.domain.manage.animation.db.AnimationCraftArchiveService;
import com.biology.domain.manage.animation.db.AnimationEntity;
import com.biology.domain.manage.animation.db.AnimationService;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class AnimationModel extends AnimationEntity {
    private AnimationService animationService;

    private AnimationCraftArchiveService animationCraftProcessService;

    public AnimationModel(AnimationService animationService,
            AnimationCraftArchiveService animationCraftProcessService) {
        this.animationService = animationService;
        this.animationCraftProcessService = animationCraftProcessService;
    }

    public AnimationModel(AnimationEntity entity, AnimationService animationService,
            AnimationCraftArchiveService animationCraftProcessService) {
        if (entity != null) {
            BeanUtils.copyProperties(entity, this);
        }
        this.animationService = animationService;
        this.animationCraftProcessService = animationCraftProcessService;
    }

    public void loadAddCommand(AddAnimationCommand command) {
        if (command != null) {
            BeanUtils.copyProperties(command, this, "animationId");
        }
    }

    public void loadUpdateCommand(UpdateAnimationCommand command) {
        if (command != null) {
            loadAddCommand(command);
        }
    }
}
