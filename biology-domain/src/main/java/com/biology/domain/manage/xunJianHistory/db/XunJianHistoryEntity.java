package com.biology.domain.manage.xunJianHistory.db;

import java.io.Serializable;
import java.util.List;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.biology.common.core.base.BaseEntity;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@TableName(value = "manage_xun_jian_history", autoResultMap = true)
@ApiModel(value = "XunJianEntity对象", description = "巡检表")
public class XunJianHistoryEntity extends BaseEntity<XunJianHistoryEntity> {
    private static final long serialVersionUID = 1L;

    @TableId(value = "xun_jian_history_id", type = IdType.AUTO)
    private Long xunJianHistoryId;

    @TableField(value = "fan_wei")
    private String fanWei;

    @TableField(value = "xun_jian_lei_xing")
    private String xunJianLeiXing;

    @TableField(value = "xun_jian_pin_lu")
    private String xunJianPinLu;

    @TableField(value = "xun_jian_id")
    private Long xunJianId;

    @TableField(value = "status")
    private String status;

    @TableField(value = "time_range", typeHandler = JacksonTypeHandler.class)
    private List<Integer> timeRange;

    @TableField(value = "day_range", typeHandler = JacksonTypeHandler.class)
    private List<Integer> dayRange;

    @Override
    public Serializable pkVal() {
        return this.xunJianHistoryId;
    }
}
