package com.biology.domain.manage.knowledge.query;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.biology.common.core.page.AbstractPageQuery;
import com.biology.domain.manage.knowledge.db.KnowledgeEntity;

import cn.hutool.core.util.StrUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class SearchKnowledgeQuery extends AbstractPageQuery<KnowledgeEntity> {
    private String title;
    private String knowledgeType;

    @Override
    public QueryWrapper<KnowledgeEntity> addQueryCondition() {
        QueryWrapper<KnowledgeEntity> queryWrapper = new QueryWrapper<KnowledgeEntity>();
        queryWrapper.like(StrUtil.isNotEmpty(title), "title", title)
                .eq(StrUtil.isNotEmpty(knowledgeType), "knowledge_type", knowledgeType);
        setTimeRangeColumn("create_time");

        return queryWrapper;
    }
}
