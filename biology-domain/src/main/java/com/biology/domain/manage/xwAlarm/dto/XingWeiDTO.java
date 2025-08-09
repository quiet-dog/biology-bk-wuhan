package com.biology.domain.manage.xwAlarm.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class XingWeiDTO {
    @JsonProperty("alarm_id")
    private Long alarmId;

    @JsonProperty("seat_number")
    private String seatNumber;

    @JsonProperty("pic_path")
    private String picPath;

    @JsonProperty("pic_path_org")
    private String picPathOrg;

    @JsonProperty("time_stamp")
    private Double timeStamp;

    @JsonProperty("camera_id")
    private String cameraId;

    private Long flag;

    @JsonProperty("filter_flag")
    private Long filterFlag;

    @JsonProperty("function_type")
    private Long functionType;

    @JsonProperty("display_flag")
    private Long displayFlag;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSS")
    private LocalDateTime createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSS")
    private LocalDateTime pushTime;

    public Long getTimeStampAsMillis() {
        if (timeStamp == null)
            return null;
        return Math.round(timeStamp * 1000);
    }
}
