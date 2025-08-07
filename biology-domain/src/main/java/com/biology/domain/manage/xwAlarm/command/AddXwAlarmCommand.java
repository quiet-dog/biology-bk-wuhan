package com.biology.domain.manage.xwAlarm.command;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class AddXwAlarmCommand {

    private Long alarmId;

    private String cameraId;

    private String picPath;

    private String seatNumber;

    private String picPathOrg;

    private Long timeStamp;

    private Long flag;

    private Long filterFlag;

    private Long functionType;

    private Long displayFlag;

}
