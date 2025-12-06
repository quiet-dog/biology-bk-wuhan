package com.biology.domain.manage.nongDuData.dto;

import java.util.Date;

import org.springframework.beans.BeanUtils;

import com.biology.common.annotation.ExcelColumn;
import com.biology.common.annotation.ExcelSheet;
import com.biology.domain.manage.nongDuData.db.NongDuDataEntity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@ExcelSheet(name = "浓度数据列表")
public class NongDuDataDTO {

    @Schema(name = "浓度数据ID")
    @ExcelColumn(name = "浓度数据ID")
    private Long nongDuDataId;

    @Schema(name = "设备sn号")
    // @ExcelColumn(name = "设备sn")
    private String deviceSn;

    @Schema(name = "粒子浓度")
    @ExcelColumn(name = "粒子浓度")
    private Double particleConcentration;

    @ExcelColumn(name = "生物浓度")
    @Schema(name = "生物浓度")
    private Double biologicalConcentration;

    private Integer alarm;

    @ExcelColumn(name = "报警状态")
    private String alarmStatus;

    private Integer workingStatus;

    @ExcelColumn(name = "工作状态")
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
    }
}
