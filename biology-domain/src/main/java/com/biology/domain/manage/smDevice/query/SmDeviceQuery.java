package com.biology.domain.manage.smDevice.query;

import java.util.ArrayList;
import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.biology.common.core.page.AbstractPageQuery;
import com.biology.domain.common.cache.CacheCenter;
import com.biology.domain.manage.smDevice.db.SmDeviceEntity;
import com.biology.domain.manage.task.dto.SmOnlineDataDTO;

import cn.hutool.cache.CacheUtil;
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

    private String online;

    private List<Long> smDeviceIds;

    @Override
    public QueryWrapper<SmDeviceEntity> addQueryCondition() {

        QueryWrapper<SmDeviceEntity> queryWrapper = new QueryWrapper<SmDeviceEntity>();
        queryWrapper
                .eq(!StrUtil.isEmpty(getDeviceSn()), "device_sn", getDeviceSn())
                .eq(!StrUtil.isEmpty(getArea()), "area", getArea())
                .eq(!StrUtil.isEmpty(getName()), "name", getName())
                .in(smDeviceIds != null && smDeviceIds.size() > 0, "sm_device_id", smDeviceIds)
                .inSql(!StrUtil.isEmpty(getPersonnelName()), "personnel_id",
                        String.format("select personnel_id from manage_personnel where name like '%s'",
                                getPersonnelName()));

        if (!StrUtil.isEmpty(getOnline())) {
            List<SmOnlineDataDTO> l = CacheCenter.smDeviceOnlineCache.getAllCache();
            List<String> sns = new ArrayList<>();
            for (SmOnlineDataDTO a : l) {
                if (a.getOnline()) {
                    sns.add(a.getSn());
                }
            }

            if (getOnline().equals("在线")) {
                if (sns != null && sns.size() > 0) {
                    queryWrapper.in("device_sn", sns);
                } else {
                    queryWrapper.eq("device_sn", "========================");
                }
            } else {
                if (sns != null && sns.size() > 0) {
                    queryWrapper.notIn("device_sn", sns);
                }
            }
        }
        return queryWrapper;
    }

}
