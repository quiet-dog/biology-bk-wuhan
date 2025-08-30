package com.biology.domain.manage.nongDuDevice.query;

import java.util.ArrayList;
import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.biology.common.core.page.AbstractPageQuery;
import com.biology.domain.common.cache.CacheCenter;
import com.biology.domain.manage.caiYangData.dto.CaiYangFunDTO;
import com.biology.domain.manage.nongDuDevice.db.NongDuDeviceEntity;
import com.biology.domain.manage.xsDevice.db.XsDeviceEntity;

import cn.hutool.core.util.StrUtil;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class NongDuDeviceQuery extends AbstractPageQuery<NongDuDeviceEntity> {

    @Schema(description = "设备sn")
    private String deviceSn;

    @Schema(description = "区域")
    private String area;

    private List<Long> nongDuDeviceIds;

    private String online;

    private String workStatus;

    @Override
    public QueryWrapper<NongDuDeviceEntity> addQueryCondition() {
        QueryWrapper<NongDuDeviceEntity> qWrapper = new QueryWrapper<>();
        qWrapper.like(!StrUtil.isEmpty(deviceSn), "device_sn", deviceSn)
                .like(!StrUtil.isEmpty(area), "area", area)
                .in(nongDuDeviceIds != null && nongDuDeviceIds.size() > 0, "nong_du_device_id", nongDuDeviceIds);

        if (!StrUtil.isEmpty(online)) {
            List<CaiYangFunDTO> list = CacheCenter.caiYangFunCache.getAllCache();
            List<String> deviceSns = new ArrayList<>();
            for (CaiYangFunDTO a : list) {
                deviceSns.add(a.getDeviceName());
            }
            if (online.equals("在线")) {
                if (deviceSns != null && deviceSns.size() > 0) {
                    qWrapper.in("device_sn", deviceSns);
                } else {
                    qWrapper.eq("device_sn", "========================");
                }
            } else {
                if (deviceSns != null && deviceSns.size() > 0) {
                    qWrapper.notIn("device_sn", deviceSns);
                }
            }
        }

        if (!StrUtil.isEmpty(workStatus)) {
            List<CaiYangFunDTO> list = CacheCenter.caiYangFunCache.getAllCache();
            List<String> deviceSns = new ArrayList<>();
            Integer workStatusInt = Integer.parseInt(workStatus);
            for (CaiYangFunDTO a : list) {
                // deviceSns.add(a.getDeviceName());
                if (a.getWorkStatus() != null && a.getWorkStatus().equals(workStatusInt)) {
                    deviceSns.add(a.getDeviceName());
                }
            }

            if (deviceSns != null && deviceSns.size() > 0) {
                qWrapper.in("device_sn", deviceSns);
            } else {
                qWrapper.eq("device_sn", "========================");
            }

        }

        return qWrapper;
    }

}
