package com.biology.domain.manage.xwAlarm.dto;

import java.util.Date;

import org.springframework.beans.BeanUtils;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.biology.common.annotation.ExcelColumn;
import com.biology.domain.manage.xwAlarm.db.XwAlarmEntity;
import com.biology.domain.manage.xwDevice.db.XwDeviceEntity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class XwAlarmDTO {

    @ExcelColumn(name = "报警序号")
    @Schema(description = "报警ID")
    private Long alarmId;

    @ExcelColumn(name = "摄像头ID")
    @Schema(description = "摄像头ID")
    private String cameraId;

    @Schema(description = "转换后图片路径")
    private String picPath;

    @ExcelColumn(name = "机位号")
    @Schema(description = "位号")
    private String seatNumber;

    @Schema(description = "未转换后的路径")
    private String picPathOrg;

    @Schema(description = "时间戳")
    private Long timeStamp;

    @Schema(description = "报警标志0未审核 1报警 2误报")
    private Long flag;

    // @ExcelColumn(name = "报警标志")
    private String flagStr;

    @Schema(description = "过滤标志")
    private Long filterFlag;

    @Schema(description = "功能类型")
    private Long functionType;

    @Schema(description = "显示标志")
    private Long displayFlag;

    @Schema(description = "行为报警ID")
    private Long xwAlarmId;

    @Schema(description = "内容")
    @ExcelColumn(name = "机位对应内容")
    private String content;

    @ExcelColumn(name = "报警时间")
    private Date createTime;

    private Date updateTime;

    private Long creatorId;

    private String area;

    public XwAlarmDTO(XwAlarmEntity entity) {
        if (entity != null) {
            BeanUtils.copyProperties(entity, this);
            addFlag();
        }
    }

    public void addFlag() {
        if (getFlag() != null) {
            if (getFlag() == 0) {
                setFlagStr("未审核");
            }
            if (getFlag() == 1) {
                setFlagStr("报警");
            }
            if (getFlag() == 2) {
                setFlagStr("误报");
            }
        }

        if (getSeatNumber() != null) {
            QueryWrapper<XwDeviceEntity> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("seat_number", getSeatNumber());
            XwDeviceEntity xwDeviceEntity = new XwDeviceEntity().selectOne(queryWrapper);
            if (xwDeviceEntity != null) {
                setContent(xwDeviceEntity.getContent());
            }
        }

        if (getSeatNumber() != null) {
            QueryWrapper<XwDeviceEntity> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("seat_number", getSeatNumber());
            XwDeviceEntity xwDeviceEntity = new XwDeviceEntity().selectOne(queryWrapper);
            if (xwDeviceEntity != null) {
                setArea(xwDeviceEntity.getContent());
            }
        }

    }

}
