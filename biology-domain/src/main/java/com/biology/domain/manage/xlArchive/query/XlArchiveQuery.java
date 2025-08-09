package com.biology.domain.manage.xlArchive.query;

import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.biology.common.core.page.AbstractPageQuery;
import com.biology.domain.manage.xlArchive.db.XlArchiveEntity;
import com.biology.domain.manage.xlFangAn.db.XlFangAnEntity;

import cn.hutool.core.util.StrUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class XlArchiveQuery extends AbstractPageQuery<XlArchiveEntity> {

    private String name;

    private String deptName;

    private List<Long> userIds;

    @Override
    public QueryWrapper<XlArchiveEntity> addQueryCondition() {
        QueryWrapper<XlArchiveEntity> qWrapper = new QueryWrapper<XlArchiveEntity>();
        qWrapper.inSql(!StrUtil.isEmpty(getName()), "personnel_id",
                String.format("select personnel_id from manage_personnel where name like '%%%s%%'", getName()))
                .inSql(!StrUtil.isEmpty(deptName), "personnel_id",
                        "select personnel_id from manage_personnel where department like '%" + getDeptName() + "%'")
                .in(getUserIds() != null && getUserIds().size() > 0, "user_id", getUserIds());

        return qWrapper;
    }

}
