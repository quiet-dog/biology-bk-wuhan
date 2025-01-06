package com.biology.domain.manage.notification.dto;

import java.util.Date;

import com.biology.domain.manage.notification.db.NotificationEntity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.biology.domain.manage.notification.db.UserNotificationEntity;

@Data
public class NotificationDTO {
    @Schema(description = "通知ID")
    private Long notificationId;

    @Schema(description = "标题")
    private String notificationTitle;

    @Schema(description = "内容")
    private String notificationContent;

    @Schema(description = "类型")
    private String notificationType;

    @Schema(description = "重要程度")
    private Integer importance;

    @Schema(description = "发送时间")
    private Date sendTime;

    @Schema(description = "读取状态")
    private Integer readStatus;

    @Schema(description = "事件Id")
    private Long eventId;

    @Schema(description = "巡检记录ID")
    private Long inspectionRecordId;

    @Schema(description = "读取时间")
    private Date readTime;

    public NotificationDTO(NotificationEntity entity, Long userId) {
        if (entity != null) {
            BeanUtils.copyProperties(entity, this);
        }
        readStatus = 0;
        readTime = null;

        sendTime = entity.getCreateTime();

        // 查询关联表里的数据
        QueryWrapper<UserNotificationEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("notification_id", this.getNotificationId());
        queryWrapper.eq("user_id", userId);
        // 修复:需要先创建 UserNotificationEntity 实例
        UserNotificationEntity userNotificationEntity = new UserNotificationEntity();
        userNotificationEntity = userNotificationEntity.selectOne(queryWrapper);
        if (userNotificationEntity != null) {
            this.readTime = userNotificationEntity.getReadTime();
            this.readStatus = 1;
        }
    }
}