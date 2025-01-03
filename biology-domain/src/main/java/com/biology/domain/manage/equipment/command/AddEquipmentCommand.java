package com.biology.domain.manage.equipment.command;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;

@Data
public class AddEquipmentCommand {

    @Schema(description = "设备编号")
    private String equipmentCode;

    @Schema(description = "设备名称")
    private String equipmentName;

    @Schema(description = "设备型号")
    private String equipmentType;

    @Schema(description = "生产厂家")
    private String manufacturer;

    @Schema(description = "购置日期")
    private Date purchaseDate;

    @Schema(description = "安装位置")
    private String installationLocation;

    @Schema(description = "使用状态")
    private String usageStatus;

    @Schema(description = "需要采集生物安全数据名称")
    private String biosafetydataName;

    @Schema(description = "房间号")
    private String roomNumber;

    @Schema(description = "技术规格")
    private String technicalSpec;

    @Schema(description = "性能参数")
    private String performanceParams;
}
