package com.biology.domain.manage.smDevice.query;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.biology.common.core.page.AbstractPageQuery;
import com.biology.domain.manage.smDevice.db.SmDeviceEntity;

import cn.hutool.core.util.StrUtil;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class SmDeviceQuery extends AbstractPageQuery<SmDeviceEntity> {
    @Schema(description = "设备sn")
    private String deviceSn;

    @Schema(description = "设备名称")
    private String name;

    @Schema(description = "操作人名称")
    private String personnelName;

    @Schema(description = "区域")
    private String area;

    @Schema(description = "是否在线")
    private Boolean isOnline;

    @Override
    public QueryWrapper<SmDeviceEntity> addQueryCondition() {

        QueryWrapper<SmDeviceEntity> queryWrapper = new QueryWrapper<SmDeviceEntity>();
        queryWrapper
                .eq(!StrUtil.isEmpty(getDeviceSn()), "device_sn", getDeviceSn())
                .eq(!StrUtil.isEmpty(getArea()), "area", getArea())
                .eq(!StrUtil.isEmpty(getName()), "name", getName())
                .inSql(!StrUtil.isEmpty(getPersonnelName()), "personnel_id",
                        String.format("select personnel_id from manage_personnel where name like '%s'",
                                getPersonnelName()));
        return queryWrapper;
    }

}
