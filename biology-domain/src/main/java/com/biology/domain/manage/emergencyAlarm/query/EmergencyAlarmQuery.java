package com.biology.domain.manage.emergencyAlarm.query;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.biology.common.core.page.AbstractPageQuery;
import com.biology.domain.manage.emergencyAlarm.db.EmergencyAlarmEntity;

import cn.hutool.core.util.StrUtil;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class EmergencyAlarmQuery extends AbstractPageQuery<EmergencyAlarmEntity> {

    @Schema(description = "报警类型")
    private String type;

    @Schema(description = "报警类型")
    private String eventName;

    @Schema(description = "报警级别")
    private String level;

    private String exportType;

    private String dStartTime;

    private String dEndTime;

    private String dType;

    @Override
    public QueryWrapper<EmergencyAlarmEntity> addQueryCondition() {
        QueryWrapper<EmergencyAlarmEntity> queryWrapper = new QueryWrapper<EmergencyAlarmEntity>();
        queryWrapper.like(StrUtil.isNotEmpty(type), "type", type)
                .like(StrUtil.isNotEmpty(level), "level", level)
                .like(StrUtil.isNotEmpty(eventName), "type", eventName)
                .inSql(StrUtil.isNotEmpty(dStartTime) && StrUtil.isNotEmpty(dEndTime), "emergency_alarm_id",
                        String.format(
                                "select emergency_alarm_id from manage_emergency_event_alarm where emergency_event_id in (select emergency_event_id from manage_emergency_event where create_time BETWEEN '%s 00:00:00' AND '%s 23:59:59')",
                                dStartTime, dEndTime))
                .inSql(StrUtil.isNotEmpty(dType), "emergency_alarm_id", String.format(
                        "select emergency_alarm_id from manage_emergency_event_alarm where emergency_event_id in (select emergency_event_id from manage_emergency_event where type = '%s')",
                        dType));

        setTimeRangeColumn("create_time");

        return queryWrapper;
    }

}
