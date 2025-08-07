package com.biology.domain.manage.xwAlarm.dto;

import java.util.Date;

import org.springframework.beans.BeanUtils;

import com.biology.domain.manage.xwAlarm.db.XwAlarmEntity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class XwAlarmDTO {

    @Schema(description = "报警ID")
    private Long alarmId;

    @Schema(description = "摄像头ID")
    private String cameraId;

    @Schema(description = "转换后图片路径")
    private String picPath;

    @Schema(description = "位号")
    private String seatNumber;

    @Schema(description = "未转换后的路径")
    private String picPathOrg;

    @Schema(description = "时间戳")
    private Long timeStamp;

    @Schema(description = "报警标志0未审核 1报警 2误报")
    private Long flag;

    @Schema(description = "过滤标志")
    private Long filterFlag;

    @Schema(description = "功能类型")
    private Long functionType;

    @Schema(description = "显示标志")
    private Long displayFlag;

    @Schema(description = "行为报警ID")
    private Long xwAlarmId;

    private Date createTime;

    private Date updateTime;

    private Long creatorId;

    public XwAlarmDTO(XwAlarmEntity entity) {
        if (entity != null) {
            BeanUtils.copyProperties(entity, this);
        }
    }

}
