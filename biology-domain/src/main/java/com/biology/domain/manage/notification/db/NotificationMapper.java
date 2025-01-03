package com.biology.domain.manage.notification.db;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.biology.domain.manage.notification.dto.NotificationDTO;

public interface NotificationMapper extends BaseMapper<NotificationEntity> {

    @Select("SELECT * FROM manage_notification WHERE notification_id = #{notificationId}")
    NotificationDTO getById(Long notificationId);
}
