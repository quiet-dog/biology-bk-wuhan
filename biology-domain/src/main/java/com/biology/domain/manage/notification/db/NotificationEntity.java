package com.biology.domain.manage.notification.db;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.biology.common.core.base.BaseEntity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@TableName("manage_notification")
@ApiModel(value = "NotificationEntity对象", description = "通知信息表")
public class NotificationEntity extends BaseEntity<NotificationEntity> {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("通知ID")
    @TableId(value = "notification_id", type = IdType.AUTO)
    private Long notificationId;

    @ApiModelProperty("标题")
    @TableField(value = "notification_title")
    private String notificationTitle;

    @ApiModelProperty("内容")
    @TableField(value = "notification_content")
    private String notificationContent;

    @ApiModelProperty("事件id")
    @TableField("event_id")
    private Long eventId;

    @ApiModelProperty("类型")
    @TableField(value = "notification_type")
    private String notificationType;

    @ApiModelProperty("重要程度")
    @TableField(value = "importance")
    private Integer importance;

    @ApiModelProperty("发送时间")
    @TableField(value = "send_time")
    private Date sendTime;

    @ApiModelProperty("接收者ID")
    @TableField(value = "receiver_id")
    private Long receiverId;

    @ApiModelProperty("创建者ID")
    @TableField(value = "creator_id")
    private Long creatorId;

    @Override
    public Serializable pkVal() {
        return this.notificationId;
    }
}