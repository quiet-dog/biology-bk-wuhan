package com.biology.domain.manage.event.query;

import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.biology.common.core.page.AbstractPageQuery;
import com.biology.domain.manage.event.db.EventEntity;

import cn.hutool.core.util.StrUtil;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class EventSearch extends AbstractPageQuery<EventEntity> {

        @Schema(description = "报警类型")
        private String type;

        @Schema(description = "报警级别")
        private String level;

        @Schema(description = "事件Ids")
        private List<Long> eventIds;

        @Schema(description = "设备名称")
        private String deviceName;

        @Schema(description = "区域")
        private String area;

        @Override
        public QueryWrapper<EventEntity> addQueryCondition() {
                QueryWrapper<EventEntity> queryWrapper = new QueryWrapper<EventEntity>();

                queryWrapper
                                .eq(StrUtil.isNotEmpty(this.type), "type", this.type)
                                .inSql(StrUtil.isNotEmpty(this.level), "level",
                                                "select level from manage_threshold_value where level = '" + this.level
                                                                + "'")
                                .in(getEventIds() != null && getEventIds().size() > 0, "event_id", this.eventIds)
                                .inSql(StrUtil.isNotEmpty((deviceName)), "equipment_id",
                                                "select equipment_id from manage_equipment where equipment_name = '"
                                                                + deviceName + "'");
                if (StrUtil.isNotEmpty(area)) {
                        queryWrapper.or().inSql("environment_id",
                                        "select environment_id from manage_environment where e_area = '" + area
                                                        + "'")
                                        .or().inSql("equipment_id",
                                                        "select equipment_id from manage_equipment where installation_location = '"
                                                                        + area + "'");
                }

                setTimeRangeColumn("create_time");

                return queryWrapper;
        }

}
