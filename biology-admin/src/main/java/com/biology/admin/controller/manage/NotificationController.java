package com.biology.admin.controller.manage;

import java.util.List;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.biology.common.core.page.PageDTO;
import com.biology.common.core.base.BaseController;
import com.biology.domain.manage.notification.NotificationApplicationService;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

import com.biology.common.core.dto.ResponseDTO;
import com.biology.domain.manage.notification.dto.NotificationDTO;
import com.biology.domain.manage.notification.command.AddNotificationCommand;
import com.biology.domain.manage.notification.command.UpdateNotificationCommand;
import io.swagger.v3.oas.annotations.Operation;
import com.biology.domain.manage.notification.query.SearchNotificationQuery;

@Tag(name = "通知API", description = "通知的增删查改")
@RestController
@RequestMapping("/manage/notification")
@Validated
@RequiredArgsConstructor
public class NotificationController extends BaseController {

    private final NotificationApplicationService notificationApplicationService;

    @Operation(summary = "获取通知列表")
    @GetMapping
    public ResponseDTO<PageDTO<NotificationDTO>> list(SearchNotificationQuery query) {
        return ResponseDTO.ok(notificationApplicationService.getNotificationList(query));
    }

    @Operation(summary = "获取通知详情")
    @GetMapping("{notificationId}")
    public ResponseDTO<NotificationDTO> get(@PathVariable("notificationId") Long notificationId) {
        return ResponseDTO.ok(notificationApplicationService.get(notificationId));
        }

    @Operation(summary = "更新通知")
    @PutMapping("{notificationId}")
    public ResponseDTO<Void> update(@PathVariable("notificationId") Long notificationId, @RequestBody UpdateNotificationCommand command) {
        command.setNotificationId(notificationId);
        notificationApplicationService.updateNotification(command);
        return ResponseDTO.ok();
    }

    @Operation(summary = "创建通知")
    @PostMapping
    public ResponseDTO<Void> add(@RequestBody AddNotificationCommand command) {
        notificationApplicationService.addNotification(command);
        return ResponseDTO.ok();
    }

    @Operation(summary = "删除通知")
    @DeleteMapping
    public ResponseDTO<Void> delete(@RequestParam List<Long> notificationIds) {
        notificationApplicationService.deleteNotifications(notificationIds);
        return ResponseDTO.ok();
    }
}
