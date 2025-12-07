package com.biology.domain.manage.kongTiaoData.query;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.biology.common.core.page.AbstractPageQuery;
import com.biology.domain.manage.kongTiaoData.db.KongTiaoDataEntity;

import cn.hutool.core.util.StrUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class KongTiaoDataQuery extends AbstractPageQuery<KongTiaoDataEntity> {

    private String deviceSn;

    @Override
    public QueryWrapper<KongTiaoDataEntity> addQueryCondition() {
        QueryWrapper<KongTiaoDataEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(!StrUtil.isEmpty(deviceSn), "device_sn", deviceSn);

        setTimeRangeColumn("create_time");
        setOrderColumn("create_time");
        setOrderDirection("descending");
        return queryWrapper;
    }
}
