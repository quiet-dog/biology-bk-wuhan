package com.biology.domain.manage.nongDuData.query;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.biology.common.core.page.AbstractPageQuery;
import com.biology.domain.manage.nongDuData.db.NongDuDataEntity;

import cn.hutool.core.util.StrUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class NongDuDataQuery extends AbstractPageQuery<NongDuDataEntity> {
    private String deviceSn;

    private String area;

    private String workStatus;

    @Override
    public QueryWrapper<NongDuDataEntity> addQueryCondition() {
        QueryWrapper<NongDuDataEntity> queryWrapper = new QueryWrapper<NongDuDataEntity>();
        queryWrapper.like(!StrUtil.isEmpty(deviceSn), "device_sn", deviceSn)
                .eq(!StrUtil.isEmpty(workStatus), "working_status", "0".equals(workStatus) ? 0 : 1)
                .inSql(!StrUtil.isEmpty(area), "device_sn",
                        "SELECT device_sn FROM manage_jian_ce_device WHERE area like '%" + area + "%'");
        ;
        return queryWrapper;
    }

}
