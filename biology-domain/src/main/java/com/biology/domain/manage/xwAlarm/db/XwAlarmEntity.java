package com.biology.domain.manage.xwAlarm.db;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.biology.common.core.base.BaseEntity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@TableName("manage_xw_alarm")
@ApiModel(value = "ReceiveEntity对象", description = "行为报警表")
public class XwAlarmEntity extends BaseEntity<XwAlarmEntity> {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("xw_alarm_id")
    @TableId(value = "xw_alarm_id", type = IdType.AUTO)
    private Long xwAlarmId;

    @TableField(value = "alarm_id")
    private Long alarmId;

    @TableField(value = "camera_id")
    private String cameraId;

    @TableField(value = "pic_path")
    private String picPath;

    @TableField(value = "seat_number")
    private String seatNumber;

    @TableField(value = "pic_path_org")
    private String picPathOrg;

    @TableField(value = "time_stamp")
    private Long timeStamp;

    @TableField(value = "flag")
    private Long flag;

    @TableField(value = "filter_flag")
    private Long filterFlag;

    @TableField(value = "function_type")
    private Long functionType;

    @TableField(value = "display_flag")
    private Long displayFlag;

    @Override
    public Serializable pkVal() {
        return this.xwAlarmId;
    }
}
