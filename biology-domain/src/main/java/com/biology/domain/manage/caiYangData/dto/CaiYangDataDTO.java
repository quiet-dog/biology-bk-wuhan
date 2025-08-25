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
    @ExcelColumn(name = "设备sn")
    private String deviceSn;

    @ExcelColumn(name = "采样时间")
    private Long workTime;

    private Long endTime;

    @ExcelColumn(name = "结束时间")
    private String endTimeStr;

    private Long caiYangDataId;

    @ExcelColumn(name = "开始时间")
    private Date createTime;

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
            // 格式化输出
            setEndTimeStr(dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        }

    }
}
