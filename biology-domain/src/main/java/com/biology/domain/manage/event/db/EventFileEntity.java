package com.biology.domain.manage.event.db;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.biology.common.core.base.BaseEntity;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@TableName("manage_event_file")
@ApiModel(value = "EventFileEntity对象", description = "知识库文件信息表")
public class EventFileEntity extends BaseEntity<EventFileEntity> {
    private static final long serialVersionUID = 1L;

    @TableId(value = "event_id", type = IdType.AUTO)
    private Long eventId;

    @TableField(value = "path")
    private String path;

    @Override
    public Long pkVal() {
        return this.eventId;
    }
}
