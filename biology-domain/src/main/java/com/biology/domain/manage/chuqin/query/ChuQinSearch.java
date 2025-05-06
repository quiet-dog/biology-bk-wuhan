package com.biology.domain.manage.chuqin.query;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.biology.common.core.page.AbstractPageQuery;
import com.biology.domain.manage.chuqin.db.ChuQinEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ChuQinSearch extends AbstractPageQuery<ChuQinEntity> {

    private LocalDateTime chuQinTime;

    private Long name;

    @Override
    public QueryWrapper<ChuQinEntity> addQueryCondition() {
        QueryWrapper<ChuQinEntity> queryWrapper = new QueryWrapper<ChuQinEntity>();

        if (chuQinTime != null) {
            // YYYY-MM-DD
            String startTime = chuQinTime.toLocalDate().atStartOfDay().toString();
            String endTime = chuQinTime.toLocalDate().plusDays(1).atStartOfDay().toString();
            queryWrapper.between("chu_qin_time", startTime, endTime);
        }

        if (name != null && !name.equals("")) {
            queryWrapper.inSql("personnel_id",
                    "select personnel_id from manage_personnel where name like '%" + name + "%'");
        }

        setTimeRangeColumn("create_time");
        return queryWrapper;
    }

}