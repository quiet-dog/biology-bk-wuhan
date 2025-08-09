package com.biology.domain.manage.shiJuan.query;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.biology.common.core.page.AbstractPageQuery;
import com.biology.domain.manage.shiJuan.db.ShiJuanEntity;

import cn.hutool.core.util.StrUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ShiJuanQuery extends AbstractPageQuery<ShiJuanEntity> {

    private String type;

    @Override
    public QueryWrapper<ShiJuanEntity> addQueryCondition() {
        QueryWrapper<ShiJuanEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper
                .eq(!StrUtil.isEmpty(getType()), "type", getType());
        return queryWrapper;
    }

}
