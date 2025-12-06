package com.biology.domain.manage.xsDevice.dto;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.BeanUtils;

import com.biology.common.annotation.ExcelColumn;
import com.biology.common.annotation.ExcelSheet;
import com.biology.domain.common.cache.CacheCenter;
import com.biology.domain.manage.caiYangData.dto.CaiYangFunDTO;
import com.biology.domain.manage.xsData.command.XsDataFun1DTO;
import com.biology.domain.manage.xsDevice.db.XsDeviceEntity;

import cn.hutool.core.util.StrUtil;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@ExcelSheet(name = "消杀设备列表")
public class XsDeviceDTO {
    @ExcelColumn(name = "设备ID")
    @Schema(description = "设备ID")
    private Long xsDeviceId;

    @ExcelColumn(name = "设备sn号")
    @Schema(description = "设备sn号")
    private String deviceSn;

    @ExcelColumn(name = "设备名称")
    @Schema(description = "设备名称")
    private String name;

    @ExcelColumn(name = "所属区域")
    @Schema(description = "所属区域")
    private String area;

    @Schema(description = "最后通讯时间")
    private Long lastTime;

    @ExcelColumn(name = "最后通讯时间")
    private String lastTimeStr;

    @Schema(description = "在线状态")
    private Boolean isOnline;

    @ExcelColumn(name = "在线状态")
    private String isOnlineStr;

    @ExcelColumn(name = "工作状态")
    private String workStatus;

    @ExcelColumn(name = "高压状态")
    private String highStatus;

    public XsDeviceDTO(XsDeviceEntity entity) {
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
            XsDataFun1DTO cDto = CacheCenter.xsDataFun1Cache.getObjectById(getDeviceSn());
            if (cDto == null) {
                setIsOnline(false);
            } else {
                if (cDto.getOnline() != null) {
                    if (cDto.getOnline().equals(1)) {
                        setIsOnline(true);
                        if (cDto.getWorkStatus() != null && cDto.getWorkStatus().equals(1)) {
                            setWorkStatus("启动");
                        } else {
                            setWorkStatus("空闲");
                        }
                        if (cDto.getHighVoltageStatus() != null && cDto.getHighVoltageStatus().equals(1)) {
                            setHighStatus("高压状态");
                        } else {
                            setHighStatus("关闭");
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
}
