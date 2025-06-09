package com.biology.domain.manage.equipment.command;

import lombok.Data;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Date;

import javax.validation.constraints.NotBlank;

import com.biology.common.annotation.ExcelColumn;

@Data
public class ExcelEquipmentCommand {

    @Schema(description = "设备编号")
    @ExcelColumn(name = "设备编号")
    @NotBlank(message = "新系统编号不能为空")
    private String equipmentCode;

    // 设备名称
    // @Schema(description = "设备名称")
    // @ExcelColumn(name = "设备名称")
    // @NotBlank(message = "设备名称不能为空")
    // private String equipmentCurrentName;

    // 设备名称 (改造后)
    @Schema(description = "设备名称")
    @ExcelColumn(name = "设备名称")
    @NotBlank(message = "设备名称(改造后)不能为空")
    private String equipmentName;

    // 设备型号
    @Schema(description = "设备型号")
    @ExcelColumn(name = "设备型号")
    @NotBlank(message = "设备型号不能为空")
    private String equipmentType;

    // 数量
    @Schema(description = "数量")
    // @ExcelColumn(name = "数量")
    private Integer quantity;

    // 所属区域
    @Schema(description = "所属区域")
    @ExcelColumn(name = "所属区域")
    private String area;

    // 位置
    @Schema(description = "位置")
    @ExcelColumn(name = "安装位置")
    @NotBlank(message = "位置不能为空")
    private String installationLocation;

    // 房间号
    @Schema(description = "房间号")
    @ExcelColumn(name = "房间号")
    private String roomNumber;

    // 生产厂家
    @Schema(description = "生产厂家")
    @ExcelColumn(name = "生产厂家")
    private String manufacturer;

    // 需要采集生物安全数据名称
    @Schema(description = "需要采集生物安全数据名称")
    @ExcelColumn(name = "需要采集生物安全数据名称")
    private String biosafetydataName;

    // 购买日期
    @Schema(description = "购买日期")
    @ExcelColumn(name = "购置日期")
    private Date purchaseDate;

    // 技术规格
    @Schema(description = "技术规格")
    @ExcelColumn(name = "技术规格")
    private String technicalSpec;

    // 性能参数
    @Schema(description = "性能参数")
    @ExcelColumn(name = "性能参数")
    private String performanceParams;
}