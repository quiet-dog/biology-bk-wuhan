package com.biology.domain.manage.equipment.query;

import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.biology.common.core.page.AbstractPageQuery;
import com.biology.domain.manage.equipment.db.EquipmentDailyInspectionRecordEntity;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.collection.CollectionUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class SearchEquipmentDailyInspectionRecordQuery extends AbstractPageQuery<EquipmentDailyInspectionRecordEntity> {

    private String recordId;
    private String inspectionCode;
    private String startDate;
    private String endDate;
    // 根据ids查询
    private List<Long> ids;

    @Override
    public QueryWrapper<EquipmentDailyInspectionRecordEntity> addQueryCondition() {
        QueryWrapper<EquipmentDailyInspectionRecordEntity> queryWrapper = new QueryWrapper<>();
        
        if (StrUtil.isNotEmpty(recordId)) {
            queryWrapper.eq("record_id", recordId);
        }

        queryWrapper
                .ge(startDate != null, "inspection_date", startDate)
                .le(endDate != null, "inspection_date", endDate);

        if (CollectionUtil.isNotEmpty(ids)) {
            queryWrapper.in("record_id", ids);
        }

        setTimeRangeColumn("inspection_date");

        return queryWrapper;
    }
}