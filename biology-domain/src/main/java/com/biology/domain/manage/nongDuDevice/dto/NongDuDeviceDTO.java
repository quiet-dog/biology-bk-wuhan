package com.biology.domain.manage.nongDuDevice.dto;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.BeanUtils;

import com.biology.common.annotation.ExcelColumn;
import com.biology.common.annotation.ExcelSheet;
import com.biology.domain.common.cache.CacheCenter;
import com.biology.domain.manage.caiYangData.dto.CaiYangFunDTO;
import com.biology.domain.manage.nongDuDevice.db.NongDuDeviceEntity;

import cn.hutool.core.util.StrUtil;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@ExcelSheet(name = "监测设备列表")
@Schema(description = "采样设备数据")
public class NongDuDeviceDTO {

    @Schema(description = "采样设备ID")
    private Long nongDuDeviceId;

    @Schema(description = "设备sn号")
    @ExcelColumn(name = "设备编号")
    private String deviceSn;

    @Schema(description = "名称")
    @ExcelColumn(name = "设备名称")
    private String name;

    @Schema(description = "区域")
    @ExcelColumn(name = "所属区域")
    private String area;

    private Boolean isOnline;

    @ExcelColumn(name = "在线状态")
    private String isOnlineStr;

    private Long lastTime;

    @ExcelColumn(name = "末次通讯时间")
    private String lastTimeStr;

    @ExcelColumn(name = "工作状态")
    private String workStatus;

    @ExcelColumn(name = "累计工作时长")
    private String workTimeStr;

    public NongDuDeviceDTO(NongDuDeviceEntity entity) {
        if (entity != null) {
            BeanUtils.copyProperties(entity, this);
            addOther();
        }
    }

    public void addOther() {
        if (lastTime != null) {
            LocalDateTime dateTime = Instant.ofEpochMilli(lastTime)
                    .atZone(ZoneId.systemDefault())
                    .toLocalDateTime();
            setLastTimeStr(dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

        }
        if (!StrUtil.isEmpty(getDeviceSn())) {
            CaiYangFunDTO cDto = CacheCenter.caiYangFunCache.getObjectById(getDeviceSn());
            if (cDto == null) {
                setIsOnline(false);
            } else {
                if (cDto.getOnline() != null) {
                    addWorkTimeStr(cDto.getWorkTime());
                    if (cDto.getOnline().equals(1)) {
                        setIsOnline(true);
                        if (cDto.getWorkStatus() != null && cDto.getWorkStatus().equals(1)) {
                            setWorkStatus("采样");
                        } else if (cDto.getWorkStatus() != null && cDto.getWorkStatus().equals(2)) {
                            setWorkStatus("采样完成");
                        } else if (cDto.getWorkStatus() != null && cDto.getWorkStatus().equals(0)) {
                            setWorkStatus("空闲");
                        }
                    } else {
                        setIsOnline(false);
                    }
                } else {
                    setIsOnline(false);
                }
            }
        }
        if (getIsOnline()) {
            setIsOnlineStr("在线");
        } else {
            setIsOnlineStr("离线");
        }
    }

    public void addWorkTimeStr(Integer workTime) {
        if (workTime != null) {
            long hours = workTime / 3600;
            long minutes = (workTime % 3600) / 60;
            long seconds = workTime % 60;
            if (hours > 0) {
                setWorkTimeStr(String.format("%d小时%d分%d秒", hours, minutes, seconds));
            } else if (minutes > 0) {
                setWorkTimeStr(String.format("%d分%d秒", minutes, seconds));
            } else {
                setWorkTimeStr(String.format("%d秒", seconds));
            }
        }
    }
}
