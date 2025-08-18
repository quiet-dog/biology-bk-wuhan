package com.biology.domain.manage.shiJuan.query;

import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.biology.common.core.page.AbstractPageQuery;
import com.biology.domain.manage.shiJuan.db.ResultShiJuanEntity;

import cn.hutool.core.util.StrUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ResultShiJuanQuery extends AbstractPageQuery<ResultShiJuanEntity> {

    private Long creatorId;

    private Long userId;

    private String userName;

    private Boolean isNeedLastTime;

    private List<String> isNotNull;

    private List<String> isNull;

    private String deptName;

    private String type;

    private List<Long> resultIds;

    @Override
    public QueryWrapper<ResultShiJuanEntity> addQueryCondition() {
        QueryWrapper<ResultShiJuanEntity> queryWrapper = new QueryWrapper<ResultShiJuanEntity>();
        queryWrapper
                .eq(getCreatorId() != null && getCreatorId() > 0, "creator_id", getCreatorId())
                .eq(getUserId() != null && getUserId() > 0, "user_id", getUserId())
                .eq(!StrUtil.isEmpty(getType()), "type", getType())
                .inSql(!StrUtil.isEmpty(getUserName()), "user_id", String.format(
                        "select user_id from manage_xl_archive where personnel_id in (select personnel_id from manage_personnel where name like '%%%s%%' and deleted = 0) and deleted = 0",
                        getUserName()))
                .inSql(!StrUtil.isEmpty(getDeptName()), "user_id", String.format(
                        "select user_id from manage_xl_archive where personnel_id in (select personnel_id from manage_personnel where department like '%%%s%%' and deleted = 0) and deleted = 0",
                        getDeptName()))
                .isNotNull(isNeedLastTime != null && isNeedLastTime, "last_time")
                .in(getResultIds() != null && getResultIds().size() > 0, "result_id", getResultIds());
        if (isNotNull != null && isNotNull.size() > 0) {
            isNotNull.forEach(item -> {
                queryWrapper.isNotNull(item);
            });
        }

        if (isNull != null && isNull.size() > 0) {
            isNull.forEach(item -> {
                queryWrapper.isNull(item);
            });
        }
        return queryWrapper;
    }

}
