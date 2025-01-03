package com.biology.domain.manage.policies.query;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.biology.common.core.page.AbstractPageQuery;
import com.biology.domain.manage.policies.db.PoliciesEntity;

import cn.hutool.core.util.StrUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class PoliciesQuery extends AbstractPageQuery<PoliciesEntity> {

    private String policiesCode;

    private String policiesName;

    private String type;

    @Override
    public QueryWrapper<PoliciesEntity> addQueryCondition() {
        QueryWrapper<PoliciesEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper
                .like(StrUtil.isNotEmpty(getPoliciesCode()), "policies_code", policiesCode)
                .like(StrUtil.isNotEmpty(getPoliciesName()), "policies_name", policiesName)
                .eq(StrUtil.isNotEmpty(getType()), "type", type);
        // 搜索创建时间

        setTimeRangeColumn("create_time");
        // setOrderColumn("create_time");
        // setOrderDirection("descending");
        return queryWrapper;
    }

}
