package com.biology.domain.manage.animation.command;

import lombok.Data;

@Data
public class UpdateAnimationCommand extends AddAnimationCommand {
    private Long animationId; // 动画ID
}
