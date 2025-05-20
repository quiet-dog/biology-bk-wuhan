package com.biology.domain.manage.materials.query;

import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.biology.common.core.page.AbstractPageQuery;
import com.biology.domain.manage.knowledge.db.KnowledgeEntity;
import com.biology.domain.manage.materials.db.MaterialsEntity;

import cn.hutool.core.util.StrUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class SearchMaterialsQuery extends AbstractPageQuery<MaterialsEntity> {

    private String name;

    private String materialsType;

    private List<Long> materialsIds;

    @Override
    public QueryWrapper<MaterialsEntity> addQueryCondition() {
        QueryWrapper<MaterialsEntity> queryWrapper = new QueryWrapper<MaterialsEntity>();
        queryWrapper.like(StrUtil.isNotEmpty(name), "name", name)
                .like(StrUtil.isNotEmpty(materialsType), "materials_type", materialsType)
                .in(materialsIds != null && materialsIds.size() > 0, "materials_id", materialsIds);
        setTimeRangeColumn("create_time");

        return queryWrapper;
    }

}
