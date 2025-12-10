package com.biology.domain.manage.xsDevice.query;

import java.util.ArrayList;
import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.biology.common.core.page.AbstractPageQuery;
import com.biology.domain.common.cache.CacheCenter;
import com.biology.domain.manage.xlFangAn.db.XlFangAnEntity;
import com.biology.domain.manage.xsData.command.XsDataFun1DTO;
import com.biology.domain.manage.xsDevice.db.XsDeviceEntity;

import cn.hutool.core.util.StrUtil;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class XsDeviceQuery extends AbstractPageQuery<XsDeviceEntity> {

    @Schema(description = "设备sn号")
    private String deviceSn;

    @Schema(description = "设备名称")
    private String name;

    @Schema(description = "区域")
    private String area;

    private String online;

    private String workStatus;

    private List<Long> xsDeviceIds;

    @Override
    public QueryWrapper<XsDeviceEntity> addQueryCondition() {
        QueryWrapper<XsDeviceEntity> queryWrapper = new QueryWrapper<XsDeviceEntity>();
        queryWrapper
                .eq(!StrUtil.isEmpty(getDeviceSn()), "device_sn", getDeviceSn())
                .eq(!StrUtil.isEmpty(getArea()), "area", getArea())
                .in(xsDeviceIds != null && xsDeviceIds.size() > 0, "xs_device_id", xsDeviceIds);
        if (!StrUtil.isEmpty(online)) {
            List<XsDataFun1DTO> l = CacheCenter.xsDataFun1Cache.getAllCache();
            List<String> deviceSns = new ArrayList<>();
            for (XsDataFun1DTO a : l) {
                deviceSns.add(a.getDeviceName());
            }
            if (getOnline().equals("在线")) {
                if (deviceSns != null && deviceSns.size() > 0) {
                    queryWrapper.in("device_sn", deviceSns);
                } else {
                    queryWrapper.eq("device_sn", "========================");
                }
            } else {
                if (deviceSns != null && deviceSns.size() > 0) {
                    queryWrapper.notIn("device_sn", deviceSns);
                }
            }

        }

        if (!StrUtil.isEmpty(workStatus)) {
            List<XsDataFun1DTO> l = CacheCenter.xsDataFun1Cache.getAllCache();
            List<String> deviceSns = new ArrayList<>();
            for (XsDataFun1DTO a : l) {
                if (a.getWorkStatus() != null && a.getWorkStatus().equals(Integer.parseInt(workStatus))) {
                    deviceSns.add(a.getDeviceName());
                }
            }

            if (deviceSns != null && deviceSns.size() > 0) {
                queryWrapper.in("device_sn", deviceSns);
            } else {
                queryWrapper.eq("device_sn", "========================");
            }

        }

        return queryWrapper;
    }

}
