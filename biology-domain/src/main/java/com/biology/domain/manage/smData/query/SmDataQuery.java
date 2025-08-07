package com.biology.domain.manage.smData.query;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.biology.common.core.page.AbstractPageQuery;
import com.biology.domain.manage.smData.db.SmDataEntity;

import cn.hutool.core.util.StrUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class SmDataQuery extends AbstractPageQuery<SmDataEntity> {
    private String smDeviceSn;

    @Override
    public QueryWrapper<SmDataEntity> addQueryCondition() {
        QueryWrapper<SmDataEntity> queryWrapper = new QueryWrapper<SmDataEntity>();
        queryWrapper.inSql(!StrUtil.isEmpty(getSmDeviceSn()), "sm_device_id",
                String.format("select sm_device_id from manage_sm_device where device_sn like '%s'",
                        getSmDeviceSn()));

        return queryWrapper;
    }

}
