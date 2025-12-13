package com.biology.domain.manage.xwAlarm.query;

import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.biology.common.core.page.AbstractPageQuery;
import com.biology.domain.manage.xwAlarm.db.XwAlarmEntity;

import cn.hutool.core.util.StrUtil;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class XwAlarmQuery extends AbstractPageQuery<XwAlarmEntity> {

    @Schema(description = "位号")
    private String seatNumber;

    @Schema(description = "摄像头ID")
    private String cameraId;

    private List<Long> xwAlarmIds;

    @Override
    public QueryWrapper<XwAlarmEntity> addQueryCondition() {
        QueryWrapper<XwAlarmEntity> queryWrapper = new QueryWrapper<XwAlarmEntity>();
        queryWrapper.like(!StrUtil.isEmpty(getSeatNumber()), "seat_number", getSeatNumber())
                .like(!StrUtil.isEmpty(getCameraId()), "camera_id", getCameraId())
                .in(getXwAlarmIds() != null && getXwAlarmIds().size() > 0, "xw_alarm_id", getXwAlarmIds());
        return queryWrapper;
    }

}
