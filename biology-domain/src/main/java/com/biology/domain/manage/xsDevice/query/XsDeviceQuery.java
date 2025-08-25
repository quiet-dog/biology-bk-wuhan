package com.biology.domain.manage.xsDevice.query;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.biology.common.core.page.AbstractPageQuery;
import com.biology.domain.manage.xlFangAn.db.XlFangAnEntity;
import com.biology.domain.manage.xsDevice.db.XsDeviceEntity;

import cn.hutool.core.util.StrUtil;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class XsDeviceQuery extends AbstractPageQuery<XsDeviceEntity> {

    @Schema(description = "设备sn")
    private String deviceSn;

    @Schema(description = "设备名称")
    private String name;

    @Schema(description = "区域")
    private String area;

    @Override
    public QueryWrapper<XsDeviceEntity> addQueryCondition() {
        QueryWrapper<XsDeviceEntity> queryWrapper = new QueryWrapper<XsDeviceEntity>();
        queryWrapper
                .eq(!StrUtil.isEmpty(getDeviceSn()), "device_sn", getDeviceSn())
                .eq(!StrUtil.isEmpty(getArea()), "area", getArea());
        return queryWrapper;
    }

}
