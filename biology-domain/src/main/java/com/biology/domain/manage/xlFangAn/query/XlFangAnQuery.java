
package com.biology.domain.manage.xlFangAn.query;

import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.biology.common.core.page.AbstractPageQuery;
import com.biology.domain.manage.smDevice.db.SmDeviceEntity;
import com.biology.domain.manage.xlFangAn.db.XlFangAnEntity;

import cn.hutool.core.util.StrUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class XlFangAnQuery extends AbstractPageQuery<XlFangAnEntity> {

    private String name;

    private List<Long> xlFangAnIds;

    @Override
    public QueryWrapper<XlFangAnEntity> addQueryCondition() {
        QueryWrapper<XlFangAnEntity> queryWrapper = new QueryWrapper<XlFangAnEntity>();
        queryWrapper
                .like(!StrUtil.isEmpty(getName()), "name", getName())
                .in(getXlFangAnIds() != null && getXlFangAnIds().size() > 0, "xl_fang_an_id", getXlFangAnIds());
        setOrderColumn("create_time");
        setOrderDirection("descending");
        return queryWrapper;
    }

}