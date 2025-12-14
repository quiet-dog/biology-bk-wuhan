package com.biology.domain.manage.nongDuData.dto;

import java.util.Date;

import org.springframework.beans.BeanUtils;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.biology.common.annotation.ExcelColumn;
import com.biology.common.annotation.ExcelSheet;
import com.biology.domain.manage.jianCeDevice.db.JianCeDeviceEntity;
import com.biology.domain.manage.nongDuData.db.NongDuDataEntity;
import com.biology.domain.manage.nongDuDevice.db.NongDuDeviceEntity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@ExcelSheet(name = "浓度数据列表")
public class NongDuDataDTO {

    @Schema(name = "浓度数据ID")
    // @ExcelColumn(name = "浓度数据ID")
    private Long nongDuDataId;

    @Schema(name = "设备sn号")
    @ExcelColumn(name = "设备编号")
    private String deviceSn;

    @Schema(name = "粒子浓度")
    @ExcelColumn(name = "粒子浓度")
    private Double particleConcentration;

    @ExcelColumn(name = "生物浓度")
    @Schema(name = "生物浓度")
    private Double biologicalConcentration;

    private String area;

    private Integer alarm;

    @ExcelColumn(name = "报警状态")
    private String alarmStatus;

    private Integer workingStatus;

    @ExcelColumn(name = "设备工作状态")
    private String workingStatusStr;

    @ExcelColumn(name = "采样时间")
    private Date createTime;

    public NongDuDataDTO(NongDuDataEntity entity) {
        if (entity != null) {
            BeanUtils.copyProperties(entity, this);
            addOther();
        }
    }

    public void addOther() {
        if (getAlarm() != null) {
            if (getAlarm().equals(0)) {
                setAlarmStatus("未报警");
            } else {
                setAlarmStatus("报警");
            }
        }

        if (getWorkingStatus() != null) {
            if (getWorkingStatus().equals(0)) {
                setWorkingStatusStr("停止");
            } else {
                setWorkingStatusStr("启动");
            }
        }

        if (getDeviceSn() != null) {
            QueryWrapper<JianCeDeviceEntity> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("device_sn", getDeviceSn());
            JianCeDeviceEntity nongDuDeviceEntity = new JianCeDeviceEntity().selectOne(queryWrapper);
            if (nongDuDeviceEntity != null) {
                setArea(nongDuDeviceEntity.getArea());
            }
        } else {
            QueryWrapper<JianCeDeviceEntity> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("device_sn", "deviceSn");
            JianCeDeviceEntity nongDuDeviceEntity = new JianCeDeviceEntity().selectOne(queryWrapper);
            if (nongDuDeviceEntity != null) {
                setArea(nongDuDeviceEntity.getArea());
            }
            setDeviceSn("deviceSn");
        }
    }
}
