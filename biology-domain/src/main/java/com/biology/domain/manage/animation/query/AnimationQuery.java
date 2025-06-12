package com.biology.domain.manage.animation.query;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.biology.common.core.page.AbstractPageQuery;
import com.biology.domain.manage.alarm.db.AlarmEntity;
import com.biology.domain.manage.animation.db.AnimationEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class AnimationQuery extends AbstractPageQuery<AnimationEntity> {
    @Override
    public QueryWrapper<AnimationEntity> addQueryCondition() {
        QueryWrapper<AnimationEntity> queryWrapper = new QueryWrapper<AnimationEntity>();
        return queryWrapper;
    }

}
