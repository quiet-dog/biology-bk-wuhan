package com.biology.domain.manage.shiJuan.query;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.biology.common.core.page.AbstractPageQuery;
import com.biology.domain.manage.shiJuan.db.ResultShiJuanEntity;

import cn.hutool.core.util.StrUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ResultShiJuanQuery extends AbstractPageQuery<ResultShiJuanEntity> {

    private Long creatorId;

    private Long userId;

    @Override
    public QueryWrapper<ResultShiJuanEntity> addQueryCondition() {
        QueryWrapper<ResultShiJuanEntity> queryWrapper = new QueryWrapper<ResultShiJuanEntity>();
        queryWrapper
                .eq(getCreatorId() != null && getCreatorId() > 0, "creator_id", getCreatorId())
                .eq(getUserId() != null && getUserId() > 0, "user_id", getUserId());

        return queryWrapper;
    }

}
