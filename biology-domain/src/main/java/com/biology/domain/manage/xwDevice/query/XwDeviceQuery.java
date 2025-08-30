package com.biology.domain.manage.xwDevice.query;

import java.util.ArrayList;
import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.biology.common.core.page.AbstractPageQuery;
import com.biology.domain.common.cache.CacheCenter;
import com.biology.domain.manage.xwAlarm.dto.XingWeiDTO;
import com.biology.domain.manage.xwDevice.db.XwDeviceEntity;

import cn.hutool.core.util.StrUtil;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class XwDeviceQuery extends AbstractPageQuery<XwDeviceEntity> {

    @Schema(description = "摄像头ID")
    private String cameraId;

    @Schema(description = "名称")
    private String name;

    @Schema(description = "对位内容")
    private String content;

    private String seatNumber;

    private String online;

    private List<Long> xwDeviceIds;

    @Override
    public QueryWrapper<XwDeviceEntity> addQueryCondition() {
        QueryWrapper<XwDeviceEntity> queryWrapper = new QueryWrapper<XwDeviceEntity>();
        queryWrapper
                .like(!StrUtil.isEmpty(getCameraId()), "camera_id", getCameraId())
                .like(!StrUtil.isEmpty(getName()), "name", getName())
                .like(!StrUtil.isEmpty(getContent()), "content", getContent())
                .like(!StrUtil.isEmpty(getSeatNumber()), "seat_number", getSeatNumber())
                .in(getXwDeviceIds() != null && getXwDeviceIds().size() > 0, "xw_device_id", getXwDeviceIds());

        if (!StrUtil.isEmpty(getOnline())) {
            List<XingWeiDTO> l = CacheCenter.xingWeiOnlineCache.getAllCache();
            List<String> deviceSns = new ArrayList<>();
            for (XingWeiDTO a : l) {
                deviceSns.add(a.getCameraId());
            }
            if (getOnline().equals("在线")) {
                if (deviceSns != null && deviceSns.size() > 0) {
                    queryWrapper.in("camera_id", deviceSns);
                } else {
                    queryWrapper.eq("camera_id", "========================");
                }
            } else {
                if (deviceSns != null && deviceSns.size() > 0) {
                    queryWrapper.notIn("camera_id", deviceSns);
                }
            }
        }
        return queryWrapper;
    }

}
