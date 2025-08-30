package com.biology.domain.manage.kongTiaoData.command;

import lombok.Data;

@Data
public class AddKongTiaoDataCommand {

    private String deviceSn;

    // 值班工况压力设定值
    private Double dutyPressureSetValue;

    // 值班工况风量设定值
    private Double dutyAirVolumeSetValue;

    // 风阀稳定状态标志位
    private Integer windValveStableStatus;

    // 阀位反馈
    private Integer valveFeedback;

    // 强制阀位的设定值
    private Integer forceValveSetValue;

    // 强制模式开关
    private Integer forceModeSwitch;

    // PID控制积分系数
    private Double pidIntegralCoefficient;

    // PID控制比例系数
    private Double pidProportionalCoefficient;

    // 风量反馈
    private Double airVolumeFeedback;

    // 房间实际压力
    private Double roomActualPressure;

    // 工况模式
    private Integer workMode;

    // 双工况切换时间
    private Long dualWorkModeSwitchTime;

    // 风量的设定值
    private Double airVolumeSetValue;

    // 压力的设定值
    private Double pressureFeedback;
}
