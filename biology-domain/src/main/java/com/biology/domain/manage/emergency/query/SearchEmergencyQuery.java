package com.biology.domain.manage.emergency.query;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.biology.common.core.page.AbstractPageQuery;
import com.biology.domain.manage.emergency.db.EmergencyEntity;

import cn.hutool.core.util.StrUtil;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class SearchEmergencyQuery extends AbstractPageQuery<EmergencyEntity> {

    @Schema(description = "预案名称")
    private String title;

    @Schema(description = "风险类型")
    private String riskType;

    @Override
    public QueryWrapper<EmergencyEntity> addQueryCondition() {
        QueryWrapper<EmergencyEntity> queryWrapper = new QueryWrapper<>();

        queryWrapper.like(StrUtil.isNotEmpty(title), "title", title)
                .like(StrUtil.isNotEmpty(riskType), "risk_type", riskType);
        setTimeRangeColumn("create_time");

        return queryWrapper;
    }

}
