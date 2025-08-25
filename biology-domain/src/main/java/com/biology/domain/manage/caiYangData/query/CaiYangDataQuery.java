package com.biology.domain.manage.caiYangData.query;

import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.biology.common.core.page.AbstractPageQuery;
import com.biology.domain.manage.caiYangData.db.CaiYangDataEntity;
import com.biology.domain.manage.xsData.db.XsDataEntity;

import cn.hutool.core.util.StrUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class CaiYangDataQuery extends AbstractPageQuery<CaiYangDataEntity> {

    private String deviceSn;

    private List<Long> caiYangDataIds;

    @Override
    public QueryWrapper<CaiYangDataEntity> addQueryCondition() {
        QueryWrapper<CaiYangDataEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(!StrUtil.isEmpty(deviceSn), "device_sn", deviceSn)
                .in(caiYangDataIds != null && caiYangDataIds.size() > 0, "cai_yang_data_id", caiYangDataIds);
        return queryWrapper;
    }

}
