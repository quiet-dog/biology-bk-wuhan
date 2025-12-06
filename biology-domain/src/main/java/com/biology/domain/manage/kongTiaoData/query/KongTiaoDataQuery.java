package com.biology.domain.manage.kongTiaoData.query;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.biology.common.core.page.AbstractPageQuery;
import com.biology.domain.manage.kongTiaoData.db.KongTiaoDataEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class KongTiaoDataQuery extends AbstractPageQuery<KongTiaoDataEntity> {

    private String deviceSn;

    @Override
    public QueryWrapper<KongTiaoDataEntity> addQueryCondition() {
        QueryWrapper<KongTiaoDataEntity> queryWrapper = new QueryWrapper<>();

        setTimeRangeColumn("create_time");
        setOrderColumn("create_time");
        return queryWrapper;
    }
}
