package com.biology.domain.manage.report.query;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.biology.common.core.page.AbstractPageQuery;
import com.biology.domain.manage.report.db.ReportEntity;

import cn.hutool.core.util.StrUtil;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ReportQuery extends AbstractPageQuery<ReportEntity> {

    @Schema(description = "报告编号")
    private String code;

    @Schema(description = "报告类型")
    private String reportType;

    @Schema(description = "报告人姓名")
    private String reporterName;

    @Schema(description = "物料名称")
    private String materialsName;

    @Override
    public QueryWrapper<ReportEntity> addQueryCondition() {
        QueryWrapper<ReportEntity> queryWrapper = new QueryWrapper<ReportEntity>();
        queryWrapper.like(StrUtil.isNotBlank(code), "code", code)
                .eq(StrUtil.isNotBlank(reportType), "report_type", reportType)
                .inSql(StrUtil.isNotBlank(reporterName), "creator_id",
                        "select user_id from sys_user where nickname like '%" + reporterName + "%'")
                .inSql(StrUtil.isNotBlank(materialsName), "materials_id",
                        "select materials_id from manage_materials where name like '%" + materialsName + "%'");
        setTimeRangeColumn("create_time");

        return queryWrapper;
    }

}
