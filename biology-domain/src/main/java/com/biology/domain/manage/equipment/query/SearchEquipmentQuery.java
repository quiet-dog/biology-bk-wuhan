package com.biology.domain.manage.equipment.query;

import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.biology.common.core.page.AbstractPageQuery;
import com.biology.domain.manage.equipment.db.EquipmentEntity;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class SearchEquipmentQuery extends AbstractPageQuery<EquipmentEntity> {

    private String equipmentCode;
    private String equipmentName;
    // 设备类型
    private String equipmentType;
    // manufacturer
    private String manufacturer;
    // purchaseDate
    private Date startPurchaseDate;
    // endPurchaseDate
    private Date endPurchaseDate;
    // 使用状态
    private String usageStatus;

    // 根据ids查询
    private List<Long> ids;

    @Override
    public QueryWrapper<EquipmentEntity> addQueryCondition() {
        QueryWrapper<EquipmentEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper
                .like(StrUtil.isNotEmpty(equipmentCode), "equipment_code", equipmentCode)
                .like(StrUtil.isNotEmpty(equipmentType), "equipment_type", equipmentType)
                .like(StrUtil.isNotEmpty(manufacturer), "manufacturer", manufacturer)
                .eq(StrUtil.isNotEmpty(usageStatus), "usage_status", usageStatus)
                .like(StrUtil.isNotEmpty(equipmentName), "equipment_name", equipmentName)
                .orderBy(true,
                        false, "equipment_id");
        // purchaseDate
        if (startPurchaseDate != null && endPurchaseDate != null) {
            queryWrapper.between("purchase_date", startPurchaseDate, endPurchaseDate);
        }

        if (CollectionUtil.isNotEmpty(ids)) {
            queryWrapper.in("equipment_id", ids);
        }

        setTimeRangeColumn("create_time");

        // 排序
        queryWrapper.orderByDesc("create_time");

        return queryWrapper;
    }
}
