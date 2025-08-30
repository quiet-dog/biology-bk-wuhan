package com.biology.domain.manage.kongTiaoDevice.query;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.biology.common.core.page.AbstractPageQuery;
import com.biology.domain.manage.kongTiaoDevice.db.KongTiaoDeviceEntity;

import cn.hutool.core.util.StrUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class KongTiaoDeviceQuery extends AbstractPageQuery<KongTiaoDeviceEntity> {

    private String deviceSn;

    private String area;

    @Override
    public QueryWrapper<KongTiaoDeviceEntity> addQueryCondition() {
        QueryWrapper<KongTiaoDeviceEntity> queryWrapper = new QueryWrapper<>();

        if (StrUtil.isNotBlank(deviceSn)) {
            queryWrapper.like("device_sn", deviceSn);
        }

        if (StrUtil.isNotBlank(area)) {
            queryWrapper.like("area", area);
        }
        return queryWrapper;
    }
}
