package com.biology.domain.manage.detection.query;

import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.biology.common.core.page.AbstractPageQuery;
import com.biology.domain.manage.detection.db.DetectionEntity;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class DetectionQuery extends AbstractPageQuery<DetectionEntity> {

    @Schema(description = "描述")
    private String description;

    @Schema(description = "位号")
    private String tag;

    @Schema(description = "开始时间")
    private Date startTime;

    @Schema(description = "开始时间字符串")
    private String startCreateTime;

    @Schema(description = "结束时间")
    private Date endTime;

    @Override
    public QueryWrapper<DetectionEntity> addQueryCondition() {
        QueryWrapper<DetectionEntity> queryWrapper = new QueryWrapper<>();

        if (startTime != null) {
            queryWrapper = queryWrapper.ge("create_time", startTime);
        }

        if (endTime != null) {
            queryWrapper = queryWrapper.le("create_time", endTime);
        }

        if (StrUtil.isNotEmpty(startCreateTime)) {
            // 使用startCreateTime获取当天的开始和结束时间
            Date startDate = DateUtil.parse(startCreateTime);
            Date beginOfDay = DateUtil.beginOfDay(startDate);
            Date endOfDay = DateUtil.endOfDay(startDate);
            queryWrapper = queryWrapper.between("create_time", beginOfDay, endOfDay);
        }

        queryWrapper = queryWrapper
                .inSql(StrUtil.isNotEmpty(description), "environment_id",
                        "select environment_id from manage_environment where description like '%" + description + "%'")
                .inSql(StrUtil.isNotEmpty(tag), "environment_id",
                        "select environment_id from manage_environment where tag like '%" + tag + "%'");
        return queryWrapper;
    }

}
