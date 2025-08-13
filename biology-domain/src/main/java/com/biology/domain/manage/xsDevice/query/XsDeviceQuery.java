package com.biology.domain.manage.xsDevice.query;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.biology.common.core.page.AbstractPageQuery;
import com.biology.domain.manage.xlFangAn.db.XlFangAnEntity;
import com.biology.domain.manage.xsDevice.db.XsDeviceEntity;

import cn.hutool.core.util.StrUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class XsDeviceQuery extends AbstractPageQuery<XsDeviceEntity> {

    private String deviceSn;

    private String name;

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
