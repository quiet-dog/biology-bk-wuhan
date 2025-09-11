package com.biology.domain.manage.xwAlarm.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class XingWeiDTO {
    @JsonAlias("alarm_id")
    private Long alarmId;

    @JsonAlias("seat_number")
    private String seatNumber;

    @JsonAlias("pic_path")
    private String picPath;

    @JsonAlias("pic_path_org")
    private String picPathOrg;

    @JsonAlias("time_stamp")
    private Double timeStamp;

    @JsonAlias("camera_id")
    private String cameraId;

    private Long flag;

    @JsonAlias("filter_flag")
    private Long filterFlag;

    @JsonAlias("function_type")
    private Long functionType;

    @JsonAlias("display_flag")
    private Long displayFlag;

    @JsonAlias("created_at")
    private String createdAt;

    @JsonAlias("push_time")
    private String pushTime;

    public Long getTimeStampAsMillis() {
        if (timeStamp == null)
            return null;
        return Math.round(timeStamp * 1000);
    }
}
