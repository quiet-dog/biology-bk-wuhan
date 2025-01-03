package com.biology.domain.manage.equipment.query;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.biology.common.core.page.AbstractPageQuery;
import com.biology.domain.manage.equipment.db.EquipmentMaintenanceManualEntity;

import cn.hutool.core.util.StrUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;
import cn.hutool.core.collection.CollectionUtil;

import java.util.List;
@EqualsAndHashCode(callSuper = true)
@Data
public class SearchEquipmentMaintenanceManualQuery extends AbstractPageQuery<EquipmentMaintenanceManualEntity> {

    private String equipmentType;
    private String equipmentCode;
    private String equipmentName;

    // 根据ids查询
    private List<Long> ids;

    // 通过 equipmentType 和 equipmentCode 查询 equipment 表获取设备ID

    // equipment_type 和 equipment_code 已经不在 EquipmentMaintenanceManualEntity 中

    @Override
    public QueryWrapper<EquipmentMaintenanceManualEntity> addQueryCondition() {
        QueryWrapper<EquipmentMaintenanceManualEntity> queryWrapper = new QueryWrapper<>();

        // equipment_id 一定要在equipment表中存在
        queryWrapper.inSql("equipment_id", "SELECT equipment_id FROM manage_equipment WHERE deleted = 0");

        if (StrUtil.isNotEmpty(equipmentType)) {
            queryWrapper.inSql("equipment_id",
                    "SELECT equipment_id FROM manage_equipment WHERE equipment_type LIKE '%"
                            + equipmentType + "%'");
        }

        if (StrUtil.isNotEmpty(equipmentCode)) {
            queryWrapper.inSql("equipment_id",
                    "SELECT equipment_id FROM manage_equipment WHERE equipment_code LIKE '%"
                            + equipmentCode + "%'");
        }

        if (StrUtil.isNotEmpty(equipmentName)) {
            queryWrapper.inSql("equipment_id",
                    "SELECT equipment_id FROM manage_equipment WHERE equipment_name LIKE '%"
                            + equipmentName + "%'");
        }

        if (CollectionUtil.isNotEmpty(ids)) {
            queryWrapper.in("manual_id", ids);
        }
        
        setTimeRangeColumn("create_time");
        return queryWrapper;
    }
}