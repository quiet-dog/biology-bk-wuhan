package com.biology.domain.manage.moni.query;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.biology.common.core.page.AbstractPageQuery;
import com.biology.domain.manage.materials.db.MaterialsEntity;
import com.biology.domain.manage.moni.db.MoniEntity;

import cn.hutool.core.util.StrUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class SearchMoniQuery extends AbstractPageQuery<MoniEntity> {
    private String pushType;

    @Override
    public QueryWrapper<MoniEntity> addQueryCondition() {
        QueryWrapper<MoniEntity> queryWrapper = new QueryWrapper<MoniEntity>();
        queryWrapper.eq(StrUtil.isNotEmpty(pushType), "push_type", pushType);

        setTimeRangeColumn("create_time");
        return queryWrapper;
    }

}
