package com.biology.domain.manage.xunJian.query;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.biology.common.core.page.AbstractPageQuery;
import com.biology.domain.manage.xunJian.db.XunJianEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class XunJianQuery extends AbstractPageQuery<XunJianEntity> {

    @Override
    public QueryWrapper<XunJianEntity> addQueryCondition() {
        QueryWrapper<XunJianEntity> queryWrapper = new QueryWrapper<>();
        return queryWrapper;
    }

}
