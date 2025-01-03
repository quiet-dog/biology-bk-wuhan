package com.biology.domain.manage.notification.db;

import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

public interface UserNotificationMapper extends BaseMapper<UserNotificationEntity> {

    @Select("SELECT * FROM manage_user_notification WHERE user_id = #{userId} AND notification_id = #{notificationId}")
    UserNotificationEntity getByUserIdAndNotificationId(Long userId, Long notificationId);
} 
