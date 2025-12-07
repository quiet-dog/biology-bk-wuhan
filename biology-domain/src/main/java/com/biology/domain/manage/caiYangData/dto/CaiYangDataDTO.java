package com.biology.domain.manage.caiYangData.dto;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import org.springframework.beans.BeanUtils;

import com.biology.common.annotation.ExcelColumn;
import com.biology.common.annotation.ExcelSheet;
import com.biology.domain.manage.caiYangData.db.CaiYangDataEntity;

import lombok.Data;

@Data
@ExcelSheet(name = "采样历史数据")
public class CaiYangDataDTO {
    @ExcelColumn(name = "设备sn号")
    private String deviceSn;

    @ExcelColumn(name = "采样时间")
    private Long workTime;

    private Long endTime;

    @ExcelColumn(name = "结束时间")
    private String endTimeStr;

    private Long caiYangDataId;

    @ExcelColumn(name = "开始时间")
    private Date createTime;

    @ExcelColumn
    private String runTime;

    public CaiYangDataDTO(CaiYangDataEntity entity) {
        if (entity != null) {
            BeanUtils.copyProperties(entity, this);
            addOther();
        }
    }

    public void addOther() {
        if (endTime != null) {
            // 转换为 LocalDateTime（指定时区）
            LocalDateTime dateTime = Instant.ofEpochMilli(endTime)
                    .atZone(ZoneId.systemDefault())
                    .toLocalDateTime();
            // 计算差值 s
            long diffMillis = endTime - createTime.getTime();
            long seconds = diffMillis / 1000;
            long hours = seconds / 3600;
            long minutes = (seconds % 3600) / 60;
            long secs = seconds % 60;
            if (hours > 0) {
                setRunTime(String.format("%d小时%d分%d秒", hours, minutes, secs));
            } else if (minutes > 0) {
                setRunTime(String.format("%d分%d秒", minutes, secs));
            } else {
                setRunTime(String.format("%d秒", secs));
            }
            // 格式化输出
            setEndTimeStr(dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        }

    }
}
