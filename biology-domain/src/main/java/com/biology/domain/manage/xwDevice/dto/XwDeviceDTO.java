package com.biology.domain.manage.xwDevice.dto;

import java.util.Date;

import org.springframework.beans.BeanUtils;

import com.baomidou.mybatisplus.annotation.TableField;
import com.biology.common.annotation.ExcelSheet;
import com.biology.domain.manage.xwDevice.db.XwDeviceEntity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@ExcelSheet(name = "行为设备列表")
@Data
public class XwDeviceDTO {
    @Schema(description = "设备ID")
    private Long xwDeviceId;

    @Schema(description = "摄像头ID")
    private String cameraId;

    @Schema(description = "设备名称")
    private String name;

    @Schema(description = "设备位号")
    private String seatNumber;

    @Schema(description = "对位内容")
    private String content;

    private Date createTime;

    private Date updateTime;

    private Long creatorId;

    public XwDeviceDTO(XwDeviceEntity entity) {
        if (entity != null) {
            BeanUtils.copyProperties(entity, this);
        }
    }
}
