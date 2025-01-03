package com.biology.domain.manage.notification.command;

import lombok.Data;

@Data
public class UpdateNotificationCommand extends AddNotificationCommand {
    private Long notificationId;
}
