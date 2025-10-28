package com.biology.domain.manage.runTime.query;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.biology.common.core.page.AbstractPageQuery;
import com.biology.domain.manage.report.db.ReportEntity;
import com.biology.domain.manage.runTime.db.RunTimeEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class RunTimeQuery extends AbstractPageQuery<RunTimeEntity> {
    @Override
    public QueryWrapper<RunTimeEntity> addQueryCondition() {
        QueryWrapper<RunTimeEntity> queryWrapper = new QueryWrapper<>();
        return queryWrapper;
    }

}
