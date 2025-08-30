package com.biology.domain.manage.kongTiaoData.db;

import java.io.Serializable;

import org.intellij.lang.annotations.JdkConstants.TabLayoutPolicy;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.biology.common.core.base.BaseEntity;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@TableName(value = "manage_kong_tiao_data", autoResultMap = true)
@ApiModel(value = "KongTiaoDataEntity对象", description = "空调数据表")
public class KongTiaoDataEntity extends BaseEntity<KongTiaoDataEntity> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "kong_tiao_data_id", type = IdType.AUTO)
    private Long kongTiaoDataId;

    @TableField(value = "device_sn")
    private String deviceSn;

    // 值班工况压力设定值
    @TableField(value = "duty_pressure_set_value")
    private Double dutyPressureSetValue;

    // 值班工况风量设定值
    @TableField(value = "duty_air_volume_set_value")
    private Double dutyAirVolumeSetValue;

    // 风阀稳定状态标志位
    @TableField(value = "wind_valve_stable_status")
    private Integer windValveStableStatus;

    // 阀位反馈
    @TableField(value = "valve_feedback")
    private Integer valveFeedback;

    // 强制阀位的设定值
    @TableField(value = "force_valve_set_value")
    private Integer forceValveSetValue;

    // 强制模式开关
    @TableField(value = "force_mode_switch")
    private Integer forceModeSwitch;

    // PID控制积分系数
    @TableField(value = "pid_integral_coefficient")
    private Double pidIntegralCoefficient;

    // PID控制比例系数
    @TableField(value = "pid_proportional_coefficient")
    private Double pidProportionalCoefficient;

    // 风量反馈
    @TableField(value = "air_volume_feedback")
    private Double airVolumeFeedback;

    // 房间实际压力
    @TableField(value = "room_actual_pressure")
    private Double roomActualPressure;

    // 工况模式
    @TableField(value = "work_mode")
    private Integer workMode;

    // 双工况切换时间
    @TableField(value = "dual_work_mode_switch_time")
    private Long dualWorkModeSwitchTime;

    // 风量的设定值
    @TableField(value = "air_volume_set_value")
    private Double airVolumeSetValue;

    // 压力的设定值
    @TableField(value = "pressure_feedback")
    private Double pressureFeedback;

    @Override
    public Serializable pkVal() {
        return this.kongTiaoDataId;
    }
}
