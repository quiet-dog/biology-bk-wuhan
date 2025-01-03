package com.biology.domain.manage.equipment.query;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.biology.common.core.page.AbstractPageQuery;
import com.biology.domain.manage.equipment.db.EquipmentRepairRecordEntity;
import cn.hutool.core.util.StrUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;
import java.util.List;

import cn.hutool.core.collection.CollectionUtil;


@EqualsAndHashCode(callSuper = true)
@Data
public class SearchEquipmentRepairQuery extends AbstractPageQuery<EquipmentRepairRecordEntity> {

    private String equipmentCode;
    private String equipmentName;
    private String equipmentType;
    private String repairPersonnel;
    private Date startRepairDate;
    private Date endRepairDate;

    // 根据ids查询
    private List<Long> ids;

    @Override
    public QueryWrapper<EquipmentRepairRecordEntity> addQueryCondition() {
        QueryWrapper<EquipmentRepairRecordEntity> queryWrapper = new QueryWrapper<>();

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

        queryWrapper
                .like(StrUtil.isNotEmpty(repairPersonnel), "repair_personnel", repairPersonnel)
                .between(startRepairDate != null && endRepairDate != null, "repair_date", startRepairDate,
                        endRepairDate);
        setTimeRangeColumn("create_time");

        return queryWrapper;
    }
}