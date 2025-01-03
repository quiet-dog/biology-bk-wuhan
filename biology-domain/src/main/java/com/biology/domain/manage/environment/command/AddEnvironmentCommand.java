package com.biology.domain.manage.environment.command;

import java.util.List;

import com.baomidou.mybatisplus.annotation.TableField;
import com.biology.common.annotation.ExcelColumn;
import com.biology.domain.manage.alarmlevel.command.AlarmDetail;
import com.biology.domain.manage.alarmlevel.dto.AlarmlevelDetailDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class AddEnvironmentCommand {

    @Schema(description = "环境描述")
    private String description;

    // 位号
    @Schema(description = "位号")
    private String tag;

    // 类型
    @Schema(description = "类型")
    private String type;

    // 范围
    @Schema(description = "范围")
    private String scope;

    @Schema(description = "区域")
    private String eArea;

    // 信号
    @Schema(description = "信号")
    private String eSignal;

    // 设备仪表供应商
    @Schema(description = "设备仪表供应商")
    private String supplier;

    // 设备仪表型号
    @Schema(description = "设备仪表型号")
    private String model;

    // 数值
    @Schema(description = "数值")
    private double value;

    @Schema(description = "单位")
    private String unit;

    @Schema(description = "单位名称")
    @ExcelColumn(name = "单位名称")
    private String unitName;

    @Schema(description = "PLC地址")
    private String plcAddress;

    // 监测点位
    @Schema(description = "monitoring_point")
    private String monitoringPoint;

    @Schema(description = "应急预案Ids")
    private List<Long> emergencyIds;

    @Schema(description = "SOPIds")
    private List<Long> sopIds;

    @Schema(description = "报警级别信息")
    private List<AlarmDetail> alarmLevels;

}
