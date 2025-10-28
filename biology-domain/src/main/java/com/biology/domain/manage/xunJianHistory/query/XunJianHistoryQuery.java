package com.biology.domain.manage.xunJianHistory.query;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.biology.common.core.page.AbstractPageQuery;
import com.biology.domain.manage.xunJian.db.XunJianEntity;
import com.biology.domain.manage.xunJianHistory.db.XunJianHistoryEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class XunJianHistoryQuery extends AbstractPageQuery<XunJianHistoryEntity> {
    @Override
    public QueryWrapper<XunJianHistoryEntity> addQueryCondition() {
        QueryWrapper<XunJianHistoryEntity> queryWrapper = new QueryWrapper<>();
        return queryWrapper;
    }

}
