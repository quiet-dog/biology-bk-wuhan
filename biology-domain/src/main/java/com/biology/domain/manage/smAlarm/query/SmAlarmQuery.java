package com.biology.domain.manage.smAlarm.query;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.biology.common.core.page.AbstractPageQuery;
import com.biology.domain.manage.shiJuan.db.ShiJuanEntity;
import com.biology.domain.manage.smAlarm.db.SmAlarmEntity;

import cn.hutool.core.util.StrUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class SmAlarmQuery extends AbstractPageQuery<SmAlarmEntity> {

    private String deviceSn;

    @Override
    public QueryWrapper<SmAlarmEntity> addQueryCondition() {
        QueryWrapper<SmAlarmEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(!StrUtil.isEmpty(deviceSn), "device_sn", deviceSn);
        return queryWrapper;
    }

}
