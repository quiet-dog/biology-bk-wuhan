package com.biology.domain.manage.equipment.query;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.biology.common.core.page.AbstractPageQuery;
import com.biology.domain.manage.equipment.db.EquipmentInspectionRecordEntity;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.collection.CollectionUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class SearchEquipmentInspectionQuery extends AbstractPageQuery<EquipmentInspectionRecordEntity> {

    private String equipmentName;
    private String equipmentCode;
    private String equipmentType;
    private String inspector;
    private Date startInspectionDate;
    private Date endInspectionDate;

    // 根据ids查询
    private List<Long> ids;

    @Override
    public QueryWrapper<EquipmentInspectionRecordEntity> addQueryCondition() {
        QueryWrapper<EquipmentInspectionRecordEntity> queryWrapper = new QueryWrapper<>();

        // equipment_id 一定要在equipment表中存在
        queryWrapper.inSql("equipment_id", "SELECT equipment_id FROM manage_equipment WHERE deleted = 0");

        if (StrUtil.isNotEmpty(equipmentCode)) {
            queryWrapper.inSql("equipment_id",
                    "SELECT equipment_id FROM manage_equipment WHERE equipment_code LIKE '%"
                            + equipmentCode + "%'");
        }

        if (StrUtil.isNotEmpty(equipmentType)) {
            queryWrapper.inSql("equipment_id",
                    "SELECT equipment_id FROM manage_equipment WHERE equipment_type LIKE '%"
                            + equipmentType + "%'");
        }

        if (StrUtil.isNotEmpty(equipmentName)) {
            queryWrapper.inSql("equipment_id",
                    "SELECT equipment_id FROM manage_equipment WHERE equipment_name LIKE '%"
                            + equipmentName + "%'");
        }

        if (CollectionUtil.isNotEmpty(ids)) {
            queryWrapper.in("record_id", ids);
        }

        queryWrapper// 将 like 改为 eq
                .like(StrUtil.isNotEmpty(inspector), "inspector", inspector)
                .between(startInspectionDate != null && endInspectionDate != null, "inspection_date",
                        startInspectionDate, endInspectionDate);
        setTimeRangeColumn("create_time");

        return queryWrapper;
    }
}