package com.biology.domain.manage.alarmlevel.query;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.biology.common.core.page.AbstractPageQuery;
import com.biology.domain.manage.alarmlevel.db.AlarmlevelEntity;

import cn.hutool.core.util.StrUtil;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class AlarmlevelQuery extends AbstractPageQuery<AlarmlevelEntity> {

    @Schema(description = "描述")
    private String description;

    @Schema(description = "位号")
    private String tag;

    @Override
    public QueryWrapper<AlarmlevelEntity> addQueryCondition() {
        QueryWrapper<AlarmlevelEntity> queryWrapper = new QueryWrapper<AlarmlevelEntity>();

        queryWrapper.inSql(StrUtil.isNotBlank(description), "environment_id",
                "select id from manage_environment where description like '%" + description + "%'")
                .inSql(StrUtil.isNotBlank(tag), "environment_id",
                        "select id from manage_environment where tag like '%" + tag + "%'");

        setTimeRangeColumn("create_time");

        return queryWrapper;
    }

}
