package com.biology.domain.manage.xunJianHistory.db;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.biology.common.core.base.BaseEntity;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@TableName(value = "manage_xun_jian_event", autoResultMap = true)
@ApiModel(value = "XunJianEntity对象", description = "巡检表")
public class XunJianEventEntity extends BaseEntity<XunJianEventEntity> {
    private static final long serialVersionUID = 1L;

    @TableId(value = "xun_jian_history_id", type = IdType.AUTO)
    private Long xunJianHistoryId;

    @TableField(value = "event_id")
    private Long eventId;

    @TableField(value = "now_time")
    private Long nowTime;

    @Override
    public Long pkVal() {
        return this.xunJianHistoryId;
    }
}
