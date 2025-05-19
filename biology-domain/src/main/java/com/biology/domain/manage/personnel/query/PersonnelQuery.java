package com.biology.domain.manage.personnel.query;

import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.biology.common.core.page.AbstractPageQuery;
import com.biology.common.core.page.AbstractQuery;
import com.biology.domain.manage.knowledge.db.KnowledgeEntity;
import com.biology.domain.manage.personnel.db.PersonnelEntity;

import cn.hutool.core.util.StrUtil;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class PersonnelQuery extends AbstractPageQuery<PersonnelEntity> {

    private String name;

    private String department;

    private String post;

    private String education;

    @Schema(description = "联系方式")
    private String contact;

    private List<Long> personnelIds;

    @Schema(description = "导出类型")
    private String exportType;

    @Override
    public QueryWrapper<PersonnelEntity> addQueryCondition() {
        QueryWrapper<PersonnelEntity> queryWrapper = new QueryWrapper<PersonnelEntity>();
        queryWrapper.like(StrUtil.isNotEmpty(name), "name", name)
                .like(StrUtil.isNotEmpty(department), "department", department)
                .like(StrUtil.isNotEmpty(post), "post", post)
                .like(StrUtil.isNotEmpty(education), "education", education)
                .like(StrUtil.isNotEmpty(contact), "contact", contact)
                .in(personnelIds != null && personnelIds.size() > 0, "personnel_id", personnelIds)
                .between(getBeginTime() != null && getEndTime() != null, "create_time",
                        getBeginTime(), getEndTime());
        setTimeRangeColumn("create_time");

        return queryWrapper;
    }

}
