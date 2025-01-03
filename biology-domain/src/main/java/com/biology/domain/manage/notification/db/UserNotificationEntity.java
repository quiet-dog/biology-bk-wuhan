package com.biology.domain.manage.notification.db;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.biology.common.core.base.BaseEntity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@TableName("manage_user_notification")
@ApiModel(value = "UserNotificationEntity对象", description = "用户通知状态表")
public class UserNotificationEntity extends BaseEntity<UserNotificationEntity> {
    private static final long serialVersionUID = 1L;
    
    @TableField(value = "user_id")
    private Long userId;

    @ApiModelProperty("通知ID")
    @TableField(value = "notification_id") 
    private Long notificationId;

    @ApiModelProperty("读取时间")
    @TableField(value = "read_time")
    private Date readTime;
}