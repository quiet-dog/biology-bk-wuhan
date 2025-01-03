package com.biology.domain.manage.sop.query;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.biology.common.core.page.AbstractPageQuery;
import com.biology.domain.manage.sop.db.SopEntity;

import cn.hutool.core.util.StrUtil;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class SearchSopQuery extends AbstractPageQuery<SopEntity> {

    @Schema(description = "sop 名称")
    private String name;

    @Schema(description = "sop 范围")
    private String scope;

    @Override
    public QueryWrapper<SopEntity> addQueryCondition() {
        QueryWrapper<SopEntity> queryWrapper = new QueryWrapper<SopEntity>();
        queryWrapper.like(StrUtil.isNotEmpty(name), "name", name)
                .like(StrUtil.isNotEmpty(scope), "scope", scope);
        setTimeRangeColumn("create_time");

        return queryWrapper;
    }

}
