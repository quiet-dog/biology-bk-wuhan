package com.biology.domain.manage.jianCeDevice.query;

import java.util.ArrayList;
import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.biology.common.core.page.AbstractPageQuery;
import com.biology.domain.common.cache.CacheCenter;
import com.biology.domain.manage.jianCeDevice.db.JianCeDeviceEntity;
import com.biology.domain.manage.nongDuData.command.NongDuDTO;
import com.biology.domain.manage.smDevice.db.SmDeviceEntity;

import cn.hutool.core.util.StrUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class JianCeDeviceQuery extends AbstractPageQuery<JianCeDeviceEntity> {

    private String deviceSn;

    private String area;

    private String online;

    private List<Long> jianCeDeviceIds;

    @Override
    public QueryWrapper<JianCeDeviceEntity> addQueryCondition() {

        QueryWrapper<JianCeDeviceEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(!StrUtil.isEmpty(deviceSn), "device_sn", deviceSn)
                .like(!StrUtil.isEmpty(area), "area", area)
                .in(jianCeDeviceIds != null && jianCeDeviceIds.size() > 0, "jian_ce_device_id", jianCeDeviceIds);

        if (!StrUtil.isEmpty(online)) {
            List<NongDuDTO> l = CacheCenter.jianCeDataCache.getAllCache();
            List<String> deviceSns = new ArrayList<>();

            List<String> deviceSnList = new ArrayList<>();
            for (NongDuDTO a : l) {
                deviceSnList.add(a.getDeviceSn());
            }

            if (online.equals("在线")) {
                if (deviceSnList != null && deviceSnList.size() > 0) {
                    queryWrapper.in("device_sn", deviceSnList);
                } else {
                    queryWrapper.eq("device_sn", "========================");
                }
            } else {
                if (deviceSnList != null && deviceSnList.size() > 0) {
                    queryWrapper.notIn("device_sn", deviceSnList);
                }
            }
        }
        return queryWrapper;
    }

}
