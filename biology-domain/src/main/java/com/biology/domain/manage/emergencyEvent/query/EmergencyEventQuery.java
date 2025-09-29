package com.biology.domain.manage.emergencyEvent.query;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.biology.common.core.page.AbstractPageQuery;
import com.biology.domain.manage.emergencyEvent.db.EmergencyEventEntity;
import cn.hutool.core.util.StrUtil;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class EmergencyEventQuery extends AbstractPageQuery<EmergencyEventEntity> {

    @Schema(description = "事件名称")
    private String eventName;

    @Schema(description = "处理人员")
    private String handlerName;

    private String type;

    @Override
    public QueryWrapper<EmergencyEventEntity> addQueryCondition() {
        QueryWrapper<EmergencyEventEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(StrUtil.isNotEmpty(eventName), "event_name", eventName)
                .eq(StrUtil.isNotEmpty(type), "type", type)
                .inSql(StrUtil.isNotEmpty(handlerName), "emergency_event_id",
                        "select emergency_event_id from manage_emergency_event_personnel where personnel_id in (SELECT personnel_id from manage_personnel where name like '%"
                                + handlerName + "%')");
        return queryWrapper;
    }

}
