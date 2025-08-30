package com.biology.domain.manage.xwDevice.dto;

import java.util.Date;

import org.springframework.beans.BeanUtils;

import com.baomidou.mybatisplus.annotation.TableField;
import com.biology.common.annotation.ExcelColumn;
import com.biology.common.annotation.ExcelSheet;
import com.biology.domain.common.cache.CacheCenter;
import com.biology.domain.manage.xwAlarm.dto.XingWeiDTO;
import com.biology.domain.manage.xwDevice.db.XwDeviceEntity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@ExcelSheet(name = "行为设备列表")
@Data
public class XwDeviceDTO {
    @Schema(description = "设备ID")
    private Long xwDeviceId;

    @ExcelColumn(name = "摄像头ID")
    @Schema(description = "摄像头ID")
    private String cameraId;

    @ExcelColumn(name = "设备名称")
    @Schema(description = "设备名称")
    private String name;

    @ExcelColumn(name = "机位号")
    @Schema(description = "设备位号")
    private String seatNumber;

    @ExcelColumn(name = "机位对应内容")
    @Schema(description = "对位内容")
    private String content;

    @Schema(description = "在线状态")
    private String isOnlineStr;

    private Date createTime;

    private Date updateTime;

    private Long creatorId;

    public XwDeviceDTO(XwDeviceEntity entity) {
        if (entity != null) {
            BeanUtils.copyProperties(entity, this);
        }
    }

    public void addOther() {
        XingWeiDTO x = CacheCenter.xingWeiOnlineCache.getObjectById(getCameraId());
        if (x != null) {
            setIsOnlineStr("在线");
        } else {
            setIsOnlineStr("离线");
        }
    }
}
