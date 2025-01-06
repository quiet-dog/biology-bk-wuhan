package com.biology.domain.manage.notification.query;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.biology.common.core.page.AbstractPageQuery;
import com.biology.domain.manage.notification.db.NotificationEntity;
import com.biology.domain.system.user.db.SysUserEntity;
import com.biology.domain.system.user.dto.UserDetailDTO;

import cn.hutool.core.util.StrUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class SearchNotificationQuery extends AbstractPageQuery<NotificationEntity> {

    private String notificationTitle;
    private String notificationType;
    private Integer importance;
    private Long userId;
    private Boolean isPersonal;

    @Override
    public QueryWrapper<NotificationEntity> addQueryCondition() {
        QueryWrapper<NotificationEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StrUtil.isNotEmpty(notificationTitle), "notification_title", notificationTitle)
                .eq(StrUtil.isNotEmpty(notificationType), "notification_type", notificationType)
                .eq(importance != null, "importance", importance);

        if (isPersonal != null && isPersonal) {
            queryWrapper.eq("receiver_id", userId);
        } else {
            queryWrapper.and(wrapper -> wrapper.isNull("receiver_id").or().eq("receiver_id", 0));
        }

        this.timeRangeColumn = "create_time";
        queryWrapper.orderByDesc("create_time");
        return queryWrapper;
    }
}