package com.biology.domain.manage.animation.model;

import org.springframework.stereotype.Component;

import com.biology.common.exception.ApiException;
import com.biology.common.exception.error.ErrorCode.Business;
import com.biology.domain.manage.alarm.db.AlarmEntity;
import com.biology.domain.manage.alarm.model.AlarmModel;
import com.biology.domain.manage.animation.db.AnimationCraftArchiveService;
import com.biology.domain.manage.animation.db.AnimationEntity;
import com.biology.domain.manage.animation.db.AnimationService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AnimationFactory {
    private final AnimationService animationService;

    private final AnimationCraftArchiveService animationCraftProcessService;

    public AnimationModel create() {
        return new AnimationModel(animationService, animationCraftProcessService);
    }

    public AnimationModel loadById(Long id) {
        AnimationEntity alarmEntity = animationService.getById(id);
        if (alarmEntity == null) {
            throw new ApiException(Business.COMMON_OBJECT_NOT_FOUND, id, "动画");
        }
        return new AnimationModel(alarmEntity, animationService, animationCraftProcessService);
    }

}
