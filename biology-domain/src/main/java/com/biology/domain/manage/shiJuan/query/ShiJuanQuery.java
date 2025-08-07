package com.biology.domain.manage.shiJuan.query;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.biology.common.core.page.AbstractPageQuery;
import com.biology.domain.manage.shiJuan.db.ShiJuanEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ShiJuanQuery extends AbstractPageQuery<ShiJuanEntity> {
    @Override
    public QueryWrapper<ShiJuanEntity> addQueryCondition() {
        QueryWrapper<ShiJuanEntity> queryWrapper = new QueryWrapper<>();
        return queryWrapper;
    }

}
