package com.biology.domain.manage.environment.query;

import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.biology.common.core.page.AbstractPageQuery;
import com.biology.domain.manage.environment.db.EnvironmentEntity;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class EnvironmentQuery extends AbstractPageQuery<EnvironmentEntity> {

    @Schema(description = "环境描述")
    private String description;

    @Schema(description = "位点")
    private String tag;

    private List<Long> environmentIds;

    @Schema(description = "开始时间")
    private Date startCreateTime;

    private String exportType;

    @Schema(description = "单位名称")
    private String type;

    // @Schema(description = "结束时间")
    // private Date endCreateTime;

    @Override
    public QueryWrapper<EnvironmentEntity> addQueryCondition() {
        QueryWrapper<EnvironmentEntity> queryWrapper = new QueryWrapper<EnvironmentEntity>();

        if (StrUtil.isNotEmpty(description)) {
            if (description.contains("--")) {
                // 包含-- 则分割 and 查询
                String[] split = description.split("--");
                queryWrapper = queryWrapper.and(wrapper -> wrapper.like("description", split[1])
                        .and(wrapper2 -> wrapper2.like("unit_name", split[0])));
            } else {
                // 不包含-- 则按description or unit_name查询
                queryWrapper = queryWrapper
                        .and(wrapper -> wrapper.like("description", description).or().like("unit_name", description));
            }
        }

        queryWrapper
                .like(StrUtil.isNotEmpty(tag), "tag", tag)
                .in(environmentIds != null && environmentIds.size() > 0, "environment_id", environmentIds);

        if (startCreateTime != null) {
            // queryWrapper.gt("create_time", startCreateTime);

            // 取前八位
            String startTimeStr = DateUtil.format(startCreateTime, "yyyy-MM-dd");
            // 筛选当天, 通过字符串筛选
            queryWrapper = queryWrapper.like("create_time", startTimeStr + "%");
        }

        if (type != null && !type.isEmpty()) {
            queryWrapper.in("unit_name", type);
        }

        // if (endCreateTime != null) {
        // queryWrapper.lt("create_time", endCreateTime);
        // }

        // 设置时间范围条件
        // setTimeRangeColumn("create_time");

        return queryWrapper;
    }

}
