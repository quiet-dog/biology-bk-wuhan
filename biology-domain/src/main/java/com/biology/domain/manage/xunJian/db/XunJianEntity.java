package com.biology.domain.manage.xunJian.db;

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
@TableName(value = "manage_xun_jian", autoResultMap = true)
@ApiModel(value = "XunJianEntity对象", description = "巡检表")
public class XunJianEntity extends BaseEntity<XunJianEntity> {
    private static final long serialVersionUID = 1L;

    @TableId(value = "xun_jian_id", type = IdType.AUTO)
    private Long xunJianId;

    @TableField(value = "fan_wei")
    private String fanWei;

    // 巡检频率
    @TableField(value = "xun_jian_pin_lu")
    private String xunJianPinLu;

    // 巡检类型
    @TableField(value = "xun_jian_lei_xing")
    private String xunJianLeiXing;

    // 开始时间
    // @TableField(value = "start_time")
    // private Long startTime;

    // // 结束时间
    // @TableField(value = "end_time")
    // private Long endTime;

    @TableField(value = "time_range", typeHandler = JacksonTypeHandler.class)
    private List<Integer> timeRange;

    @TableField(value = "day_range", typeHandler = JacksonTypeHandler.class)
    private List<Integer> dayRange;
    // 启用/禁用
    @TableField(value = "enable")
    private Boolean enable;

    @Override
    public Serializable pkVal() {
        return this.xunJianId;
    }
}
