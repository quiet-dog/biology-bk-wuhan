package com.biology.domain.manage.notification.model;

import org.springframework.stereotype.Component;
import com.biology.common.exception.ApiException;
import com.biology.common.exception.error.ErrorCode.Business;
import com.biology.domain.manage.notification.db.NotificationEntity;
import com.biology.domain.manage.notification.db.NotificationService;
import com.biology.domain.manage.notification.db.UserNotificationService;
import com.biology.domain.manage.websocket.db.WebsocketService;
import com.biology.domain.system.user.db.SysUserService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class NotificationFactory {

    private final NotificationService notificationService;
    private final UserNotificationService userNotificationService;
    private final WebsocketService websocketService;
    private final SysUserService sysUserService;

    public NotificationModel create() {
        return new NotificationModel(notificationService, userNotificationService, websocketService, sysUserService);
    }

    public NotificationModel loadById(Long notificationId) {
        NotificationEntity notification = notificationService.getById(notificationId);

        if (notification == null) {
            throw new ApiException(Business.COMMON_OBJECT_NOT_FOUND, notificationId, "通知");
        }

        return new NotificationModel(notification, notificationService, userNotificationService, websocketService,
                sysUserService);
    }
}