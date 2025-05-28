package com.biology.domain.manage.notification.command;

import javax.validation.constraints.NotBlank;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class AddNotificationCommand {
    @Schema(description = "标题")
    @NotBlank(message = "标题不能为空")
    private String notificationTitle;

    @Schema(description = "内容")
    @NotBlank(message = "内容不能为空")
    private String notificationContent;

    @Schema(description = "类型")
    @NotBlank(message = "类型不能为空")
    private String notificationType;

    @Schema(description = "重要程度")
    // 0: 普通, 1: 重要, 2: 紧急
    private Integer importance;

    // 接受者ID
    @Schema(description = "接受者ID,一般不设置")
    private Long receiverId;

    private Long eventId;

    private Long inspectionRecordId;

    private Boolean isAdminCreate;

    // @Schema(description = "创建者ID")
    // private Long creatorId;

    // @Schema(description = "发送时间")
    // private Date sendTime;
}
