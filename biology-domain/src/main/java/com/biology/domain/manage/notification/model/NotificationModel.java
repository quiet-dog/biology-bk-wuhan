package com.biology.domain.manage.notification.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.biology.domain.manage.notification.command.AddNotificationCommand;
import com.biology.domain.manage.notification.db.NotificationEntity;
import com.biology.domain.manage.notification.db.NotificationService;
import com.biology.domain.manage.notification.db.UserNotificationEntity;
import com.biology.domain.manage.notification.db.UserNotificationService;
import com.biology.domain.manage.websocket.db.WebsocketService;

import cn.hutool.core.bean.BeanUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class NotificationModel extends NotificationEntity {

    private NotificationService notificationService;
    private UserNotificationService userNotificationService;
    private WebsocketService websocketService;

    public NotificationModel(NotificationService notificationService, UserNotificationService userNotificationService,
            WebsocketService websocketService) {
        this.notificationService = notificationService;
        this.userNotificationService = userNotificationService;
        this.websocketService = websocketService;
    }

    public NotificationModel(NotificationEntity entity, NotificationService notificationService,
            UserNotificationService userNotificationService, WebsocketService websocketService) {
        this(notificationService, userNotificationService, websocketService);
        if (entity != null) {
            BeanUtil.copyProperties(entity, this);
        }
    }

    public void loadAddNotificationCommand(AddNotificationCommand command) {
        if (command != null) {
            BeanUtil.copyProperties(command, this, "notificationId");
            this.setSendTime(new Date());
        }
    }

    // 广播通知
    public void broadcastNotification() {
        // List<UserNotificationEntity> userNotifications = new ArrayList<>();
        // userNotificationService.list().forEach(userNotification -> {
        //     UserNotificationEntity userNotificationEntity = new UserNotificationEntity();
        //     userNotificationEntity.setNotificationId(this.getNotificationId());
        //     userNotificationEntity.setUserId(userNotification.getUserId());
        //     userNotifications.add(userNotificationEntity);
        // });
        // userNotificationService.saveBatch(userNotifications);
        // 将通知推送到前端
        NotificationEntity notificationEntity = notificationService.getById(this.getNotificationId());
        System.out.println("notificationEntity = " + notificationEntity);
        websocketService.sendTopicNotice(notificationEntity);
    }

    public boolean insert() {
        super.insert();
        broadcastNotification();
        return true;
    }

    public boolean updateById() {
        super.updateById();
        return true;
    }

    public boolean deleteById() {
        super.deleteById();
        return true;
    }

}