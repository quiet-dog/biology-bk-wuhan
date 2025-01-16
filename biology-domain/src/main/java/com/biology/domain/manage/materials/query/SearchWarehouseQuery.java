package com.biology.domain.manage.materials.query;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.biology.common.core.page.AbstractPageQuery;
import com.biology.domain.manage.materials.db.MaterialsEntity;
import com.biology.domain.manage.materials.db.WarehouseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class SearchWarehouseQuery extends AbstractPageQuery<WarehouseEntity> {

    private Long materialsId;

    @Override
    public QueryWrapper<WarehouseEntity> addQueryCondition() {
        QueryWrapper<WarehouseEntity> queryWrapper = new QueryWrapper<WarehouseEntity>();
        queryWrapper.eq(materialsId != null, "materials_id", materialsId);
        return queryWrapper;
    }

}
