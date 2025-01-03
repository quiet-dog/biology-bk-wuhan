package com.biology.domain.manage.alarm.query;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.biology.common.core.page.AbstractPageQuery;
import com.biology.domain.manage.alarm.db.AlarmEntity;
import com.biology.domain.manage.alarmlevel.db.AlarmlevelEntity;

import cn.hutool.core.util.StrUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class AlarmQuery extends AbstractPageQuery<AlarmEntity> {

    private String materialsName;

    @Override
    public QueryWrapper<AlarmEntity> addQueryCondition() {
        QueryWrapper<AlarmEntity> queryWrapper = new QueryWrapper<AlarmEntity>();
        queryWrapper.inSql(StrUtil.isNotBlank(materialsName), "materials_id",
                "select materials_id from manage_materials where name like '%" + materialsName + "%'");
        setTimeRangeColumn("create_time");

        // 默认按创建时间降序
        queryWrapper.orderByDesc("create_time");

        return queryWrapper;
    }

}
