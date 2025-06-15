package com.biology.domain.manage.detection.query;

import com.biology.domain.manage.detection.db.DetectionEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.biology.common.core.page.AbstractPageQuery;

@Data
@EqualsAndHashCode(callSuper = true)
public class SearchNengHaoQuery extends AbstractPageQuery<DetectionEntity> {

    private String type;

    private Long environmentId;

    @Override
    public QueryWrapper<DetectionEntity> addQueryCondition() {

        QueryWrapper<DetectionEntity> queryWrapper = new QueryWrapper<>();
        if (type != null && !type.isEmpty()) {
            if (type.equals("电")) {
                // 查询 electron不为null的记录
                // 并且electricity_value不为0
                queryWrapper.ne("electricity_value", 0);
            }
            if (type.equals("水")) {
                // 查询 water不为null的记录
                // 并且water_value不为0
                queryWrapper.ne("water_value", 0);
            }
        } else {
            // 电和水都查询 value为null的记录
            queryWrapper.ne("electricity_value", 0)
                    .or().ne("water_value", 0);

            // value为0的记录
        }

        if (environmentId != null && environmentId > 0) {
            queryWrapper.eq("environment_id", environmentId);
        }

        this.timeRangeColumn = "create_time";
        return queryWrapper;
    }

}
