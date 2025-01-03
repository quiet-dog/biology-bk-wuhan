package com.biology.domain.manage.healthy.query;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.biology.common.core.page.AbstractPageQuery;
import com.biology.domain.manage.healthy.db.HealthyEntity;
import com.biology.domain.manage.knowledge.db.KnowledgeEntity;

import cn.hutool.core.util.StrUtil;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class HealthyQuery extends AbstractPageQuery<HealthyEntity> {

    @Schema(description = "员工姓名")
    private String name;

    @Schema(description = "是否按组最新的数据返回")
    private Boolean isGroup;

    @Override
    public QueryWrapper<HealthyEntity> addQueryCondition() {
        QueryWrapper<HealthyEntity> queryWrapper = new QueryWrapper<HealthyEntity>();
        queryWrapper
                .inSql(StrUtil.isNotEmpty(name), "personnel_id",
                        "select personnel_id from manage_personnel where name like '%" + name + "%'");
        if (isGroup != null && isGroup) {
            queryWrapper.inSql("healthy_id",
                    "select healthy_id from manage_personnel_healthy WHERE (personnel_id ,create_time) in (SELECT personnel_id,MAX(create_time) from manage_personnel_healthy group by personnel_id)");
        }
        return queryWrapper;
    }

}
