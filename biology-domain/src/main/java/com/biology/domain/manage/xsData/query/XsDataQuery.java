package com.biology.domain.manage.xsData.query;

import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.biology.common.core.page.AbstractPageQuery;
import com.biology.domain.manage.xsData.db.XsDataEntity;

import cn.hutool.core.util.StrUtil;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class XsDataQuery extends AbstractPageQuery<XsDataEntity> {

    @Schema(name = "设备sn号")
    private String deviceSn;

    @Schema(name = "区域")
    private String area;

    private List<Long> xsDataIds;

    @Override
    public QueryWrapper<XsDataEntity> addQueryCondition() {
        QueryWrapper<XsDataEntity> queryWrapper = new QueryWrapper<XsDataEntity>();
        queryWrapper.like(!StrUtil.isEmpty(deviceSn), "device_sn", deviceSn)
                .in(xsDataIds != null && xsDataIds.size() > 0, "xs_data_id", xsDataIds)
                .inSql(!StrUtil.isEmpty(area), "device_sn",
                        String.format("select device_sn from manage_xs_device where area like '%s'", area));
        setOrderColumn("create_time");
        setOrderDirection("descending");
        return queryWrapper;
    }

}