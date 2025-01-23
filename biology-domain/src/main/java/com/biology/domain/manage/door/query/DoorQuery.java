package com.biology.domain.manage.door.query;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.biology.common.core.page.AbstractPageQuery;
import com.biology.domain.manage.door.db.DoorEntity;
import com.biology.domain.manage.emergency.db.EmergencyEntity;

import cn.hutool.core.util.StrUtil;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class DoorQuery extends AbstractPageQuery<DoorEntity> {

    @Schema(description = "门禁编号")
    private String doorCode;

    @Schema(description = "门禁地点")
    private String doorPlace;

    @Schema(description = "人员姓名")
    private String name;

    @Schema(description = "是否白天")
    private Boolean IsDay;

    @Override
    public QueryWrapper<DoorEntity> addQueryCondition() {
        QueryWrapper<DoorEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StrUtil.isNotEmpty(doorCode), "door_code", doorCode)
                .like(StrUtil.isNotEmpty(doorPlace), "door_place", doorPlace)
                .inSql(StrUtil.isNotEmpty(name), "personnel_id",
                        "select personnel_id from manage_personnel where name like '%" + name + "%'");
        setTimeRangeColumn("create_time");
        return queryWrapper;
    }

}
