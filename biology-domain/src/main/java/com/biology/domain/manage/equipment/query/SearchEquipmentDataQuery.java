package com.biology.domain.manage.equipment.query;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.biology.common.core.page.AbstractPageQuery;
import com.biology.domain.manage.equipment.db.EquipmentDataEntity;
import com.biology.infrastructure.config.MyBatisConfig;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class SearchEquipmentDataQuery extends AbstractPageQuery<EquipmentDataEntity> {

    @ApiModelProperty("设备名称")
    private String equipmentName;

    @ApiModelProperty("设备编码")
    private String equipmentCode;

    @ApiModelProperty("设备型号")
    private String equipmentType;

    @ApiModelProperty("监测指标")
    private String monitoringIndicator;

    private String highNode;

    // @ApiModelProperty("开始时间")
    // private Date startTime;

    // @ApiModelProperty("结束时间")
    // private Date endTime;

    // 根据ids查询
    private List<Long> ids;

    @Override
    public QueryWrapper<EquipmentDataEntity> addQueryCondition() {
        QueryWrapper<EquipmentDataEntity> queryWrapper = new QueryWrapper<>();

        // equipment_id 一定要在equipment表中存在
        queryWrapper.inSql("equipment_id", "SELECT equipment_id FROM manage_equipment WHERE deleted = 0");

        if (StrUtil.isNotEmpty(equipmentName)) {
            queryWrapper.inSql("equipment_id",
                    "SELECT equipment_id FROM manage_equipment WHERE equipment_name LIKE '%"
                            + equipmentName + "%'");
        }

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

        if (StrUtil.isNotEmpty(monitoringIndicator)) {
            queryWrapper.inSql("threshold_id",
                    "SELECT threshold_id FROM manage_threshold WHERE equipment_index LIKE '%"
                            + monitoringIndicator + "%'");
        }

        if (StrUtil.isNotEmpty(getHighNode()) && getHighNode().equals("high")) {
            queryWrapper.inSql("equipment_id",
                    "select equipment_id from manage_craft_node_equipment where craft_node_id in (select craft_node_id from manage_craft_node where is_high_risk = 1)");
        }

        if (CollectionUtil.isNotEmpty(ids)) {
            queryWrapper.in("equipment_data_id", ids);
        }

        if (getBeginTime() != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            MyBatisConfig.equipmentData.set(sdf.format(getBeginTime()));
            setBeginTime(null);

        } else {
            MyBatisConfig.equipmentData.set(null);
        }
        // if (startTime != null) {
        // queryWrapper.ge("create_time", startTime);
        // }

        // if (endTime != null) {
        // queryWrapper.le("create_time", endTime);
        // }

        this.timeRangeColumn = "create_time";

        return queryWrapper;
    }
}