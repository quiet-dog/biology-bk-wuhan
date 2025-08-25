package com.biology.domain.manage.nongDuDevice.query;

import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.biology.common.core.page.AbstractPageQuery;
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

    @Override
    public QueryWrapper<NongDuDeviceEntity> addQueryCondition() {
        QueryWrapper<NongDuDeviceEntity> qWrapper = new QueryWrapper<>();
        qWrapper.like(!StrUtil.isEmpty(deviceSn), "device_sn", deviceSn)
                .like(!StrUtil.isEmpty(area), "area", area)
                .in(nongDuDeviceIds != null && nongDuDeviceIds.size() > 0, "nong_du_device_id", nongDuDeviceIds);

        return qWrapper;
    }

}
