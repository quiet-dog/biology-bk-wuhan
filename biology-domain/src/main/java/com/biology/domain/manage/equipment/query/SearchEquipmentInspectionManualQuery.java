package com.biology.domain.manage.equipment.query;

import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.biology.common.core.page.AbstractPageQuery;
import com.biology.domain.manage.equipment.db.EquipmentInspectionManualEntity;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class SearchEquipmentInspectionManualQuery extends AbstractPageQuery<EquipmentInspectionManualEntity> {

    private String equipmentName;
    private String equipmentType;
    private String equipmentCode;

    private String manualName;
    private String manualVersion;
    private String manualCode;

    // 根据ids查询
    private List<Long> ids;

    @Override
    public QueryWrapper<EquipmentInspectionManualEntity> addQueryCondition() {
        QueryWrapper<EquipmentInspectionManualEntity> queryWrapper = new QueryWrapper<>();

        // equipment_id 一定要在equipment表中存在
        // queryWrapper.inSql("equipment_id", "SELECT equipment_id FROM manage_equipment
        // WHERE deleted = 0");

        // if (StrUtil.isNotEmpty(equipmentName)) {
        // queryWrapper.inSql("equipment_id",
        // "SELECT equipment_id FROM manage_equipment WHERE equipment_name LIKE '%"
        // + equipmentName + "%'");
        // }

        // if (StrUtil.isNotEmpty(equipmentType)) {
        // queryWrapper.inSql("equipment_id",
        // "SELECT equipment_id FROM manage_equipment WHERE equipment_type LIKE '%"
        // + equipmentType + "%'");
        // }
        // if (StrUtil.isNotEmpty(equipmentCode)) {
        // queryWrapper.inSql("equipment_id",
        // "SELECT equipment_id FROM manage_equipment WHERE equipment_code LIKE '%"
        // + equipmentCode + "%'");
        // }

        if (CollectionUtil.isNotEmpty(ids)) {
            queryWrapper.in("manual_id", ids);
        }

        queryWrapper.like(StrUtil.isNotEmpty(manualName), "manual_name", manualName)
                .like(StrUtil.isNotEmpty(manualVersion), "manual_version", manualVersion)
                .like(StrUtil.isNotEmpty(manualCode), "manual_code", manualCode);

        this.timeRangeColumn = "create_time";
        return queryWrapper;
    }

}