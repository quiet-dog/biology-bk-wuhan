package com.biology.domain.manage.kongTiaoDevice.query;

import java.util.ArrayList;
import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.biology.common.core.page.AbstractPageQuery;
import com.biology.domain.common.cache.CacheCenter;
import com.biology.domain.manage.kongTiaoData.dto.KongTiaoDataDTO;
import com.biology.domain.manage.kongTiaoDevice.db.KongTiaoDeviceEntity;

import cn.hutool.core.util.StrUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class KongTiaoDeviceQuery extends AbstractPageQuery<KongTiaoDeviceEntity> {

    private String deviceSn;

    private String area;

    private String online;

    @Override
    public QueryWrapper<KongTiaoDeviceEntity> addQueryCondition() {
        QueryWrapper<KongTiaoDeviceEntity> queryWrapper = new QueryWrapper<>();

        queryWrapper
                .like(StrUtil.isNotBlank(deviceSn), "device_sn", deviceSn)
                .like(StrUtil.isNotBlank(area), "area", area);

        if (StrUtil.isNotBlank(online)) {
            List<String> deviceSns = new ArrayList<>();
            if (online.equals("在线")) {
                List<KongTiaoDataDTO> l = CacheCenter.kongTiaoDataCache.getAllCache();
                for (KongTiaoDataDTO a : l) {
                    if (a.getIsOnline()) {
                        deviceSns.add(a.getDeviceSn());
                    }
                }
            } else {
                List<KongTiaoDataDTO> l = CacheCenter.kongTiaoDataCache.getAllCache();
                for (KongTiaoDataDTO a : l) {
                    if (!a.getIsOnline()) {
                        deviceSns.add(a.getDeviceSn());
                    }
                }
            }
            queryWrapper = queryWrapper.in("device_sn", deviceSns);
        }
        return queryWrapper;
    }
}
