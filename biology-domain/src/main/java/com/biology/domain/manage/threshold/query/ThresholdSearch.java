package com.biology.domain.manage.threshold.query;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.biology.common.core.page.AbstractPageQuery;
import com.biology.domain.manage.sop.db.SopEntity;
import com.biology.domain.manage.threshold.db.ThresholdEntity;

import cn.hutool.core.util.StrUtil;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ThresholdSearch extends AbstractPageQuery<ThresholdEntity> {

    @Schema(description = "设备名称")
    private String equipmentName;

    @Schema(description = "设备型号")
    private String model;

    @Schema(description = "传感器型号")
    private String sensorModel;

    @Schema(description = "传感器名称")
    private String sensorName;

    @Schema(description = "设备指标")
    private String equipmentIndex;

    public QueryWrapper<ThresholdEntity> addQueryCondition() {
        QueryWrapper<ThresholdEntity> queryWrapper = new QueryWrapper<ThresholdEntity>();

        queryWrapper.inSql(StrUtil.isNotEmpty(equipmentName), "equipment_id",
                "select equipment_id from manage_equipment where equipment_name like '%" + equipmentName + "%'")
                .inSql(StrUtil.isNotEmpty(model), "equipment_id",
                        "select equipment_id from manage_equipment where model like '%" + model + "%'")
                .eq(StrUtil.isNotEmpty(sensorModel), "sensor_model", sensorModel)
                .eq(StrUtil.isNotEmpty(sensorName), "sensor_name", sensorName)
                .like(StrUtil.isNotEmpty(equipmentIndex), "equipment_index", equipmentIndex);
        setTimeRangeColumn("create_time");

        return queryWrapper;
    }
}
