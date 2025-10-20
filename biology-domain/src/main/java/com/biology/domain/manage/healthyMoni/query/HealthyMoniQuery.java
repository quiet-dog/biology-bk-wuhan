package com.biology.domain.manage.healthyMoni.query;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.biology.common.core.page.AbstractPageQuery;
import com.biology.domain.manage.healthy.db.HealthyEntity;
import com.biology.domain.manage.healthyMoni.db.HealthyMoniEntity;

import lombok.Data;

@Data
public class HealthyMoniQuery extends AbstractPageQuery<HealthyMoniEntity> {
    private Long personnelId;

    @Override
    public QueryWrapper<HealthyMoniEntity> addQueryCondition() {
        QueryWrapper<HealthyMoniEntity> queryWrapper = new QueryWrapper<>();

        queryWrapper.eq(personnelId != null && personnelId > 0, "personnel_id", personnelId);
        return queryWrapper;
    }

}
