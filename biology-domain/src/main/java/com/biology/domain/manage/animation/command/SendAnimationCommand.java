package com.biology.domain.manage.animation.command;

import java.util.List;

import lombok.Data;

@Data
public class SendAnimationCommand {
    private List<Long> animationIds; // 动画ID
    private String type;
}
