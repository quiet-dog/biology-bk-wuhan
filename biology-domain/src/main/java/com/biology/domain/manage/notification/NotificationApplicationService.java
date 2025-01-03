package com.biology.domain.manage.notification;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.biology.common.core.page.PageDTO;
import com.biology.domain.manage.equipment.db.EquipmentEntity;
import com.biology.domain.manage.equipment.dto.EquipmentDTO;
import com.biology.domain.manage.equipment.query.SearchEquipmentQuery;
import com.biology.domain.manage.notification.command.AddNotificationCommand;
import com.biology.domain.manage.notification.command.UpdateNotificationCommand;
import com.biology.domain.manage.notification.db.NotificationEntity;
import com.biology.domain.manage.notification.db.NotificationService;
import com.biology.domain.manage.notification.db.UserNotificationEntity;
import com.biology.domain.manage.notification.db.UserNotificationService;
import com.biology.domain.manage.notification.dto.NotificationDTO;
import com.biology.domain.manage.notification.model.NotificationFactory;
import com.biology.domain.manage.notification.model.NotificationModel;
import com.biology.infrastructure.user.AuthenticationUtils;
import com.biology.infrastructure.user.web.SystemLoginUser;
import com.biology.domain.manage.notification.query.SearchNotificationQuery;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class NotificationApplicationService {

    private final NotificationFactory notificationFactory;
    private final NotificationService notificationService;
    private final UserNotificationService userNotificationService;
    public Object baseMapper;

    public void addNotification(AddNotificationCommand command) {
        // 创建者
        Long userId = getUserIdSafely();
        // command.setCreatorId(userId);
        NotificationModel notificationModel = notificationFactory.create();
        notificationModel.loadAddNotificationCommand(command);
        notificationModel.setCreatorId(userId);
        notificationModel.insert();
    }

    public void updateNotification(UpdateNotificationCommand command) {
        NotificationModel notificationModel = notificationFactory.loadById(command.getNotificationId());
        notificationModel.loadAddNotificationCommand(command);
        notificationModel.updateById();
    }

    public void deleteNotifications(List<Long> notificationIds) {
        for (Long notificationId : notificationIds) {
            NotificationModel notificationModel = notificationFactory.loadById(notificationId);
            notificationModel.deleteById();
        }
    }

    public Long getUserIdSafely() {
        Long userId = null;
        try {
            SystemLoginUser loginUser = AuthenticationUtils.getSystemLoginUser();
            userId = loginUser.getUserId();
        } catch (Exception e) {
            log.warn("can not find user in current thread.");
        }
        return userId;
    }

    public PageDTO<NotificationDTO> getNotificationList(SearchNotificationQuery query) {
        // 获取当前用户ID
        Long userId = getUserIdSafely();
        query.setUserId(userId);
        Page<NotificationEntity> page = notificationService.page(query.toPage(), query.toQueryWrapper());
        List<NotificationDTO> records = page.getRecords().stream()
                .map(entity -> new NotificationDTO(entity, userId))
                .collect(Collectors.toList());
        return new PageDTO<>(records, page.getTotal());
    }

    // 查看通知详情
    public NotificationDTO get(Long notificationId) {
        // 获取用户
        Long userId = getUserIdSafely();

        NotificationEntity notification = notificationService.getById(notificationId);
        if (notification == null) {
            throw new RuntimeException("通知不存在");
        }
        // 检查用户是否已读,不存在则创建
        UserNotificationEntity userNotification = userNotificationService.getOne(
            Wrappers.<UserNotificationEntity>lambdaQuery()
                .eq(UserNotificationEntity::getUserId, userId)
                .eq(UserNotificationEntity::getNotificationId, notificationId)
        );
        if (userNotification == null) {
            userNotification = new UserNotificationEntity();
            userNotification.setUserId(userId);
            userNotification.setNotificationId(notificationId);
            userNotification.setReadTime(new Date());
            userNotificationService.save(userNotification);
        } else if (userNotification.getReadTime() == null) {
            userNotification.setReadTime(new Date());
            userNotificationService.updateById(userNotification);
        }

        return new NotificationDTO(notification, userId);
    }

}
