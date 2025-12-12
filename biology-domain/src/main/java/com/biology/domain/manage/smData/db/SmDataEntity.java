package com.biology.domain.manage.smData.db;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.biology.common.core.base.BaseEntity;

import cn.hutool.json.JSONObject;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@TableName(value = "manage_sm_data", autoResultMap = true)
@ApiModel(value = "ReceiveEntity对象", description = "生命设备数据")
public class SmDataEntity extends BaseEntity<SmDataEntity> {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("sm_data_id")
    @TableId(value = "sm_data_id", type = IdType.AUTO)
    private Long smDataId;

    @TableField(value = "sm_device_id")
    private Long smDeviceId;

    @TableField(value = "battery")
    private Double battery;

    @TableField(value = "co2")
    private Double co2;

    @TableField(value = "humility")
    private Double humility;

    @TableField(value = "temp")
    private Double temp;

    @TableField(value = "huxi", typeHandler = JacksonTypeHandler.class)
    private List<Number> huxi;

    @TableField(value = "send_time")
    private Long sendTime;

    @TableField(value = "xin_dian", typeHandler = JacksonTypeHandler.class)
    private List<Number> xinDian;

    @TableField(value = "xinlv")
    private Double xinlv;

    @TableField(value = "xue_yang")
    private Double xueYang;

    @TableField(value = "ti_tai")
    private String tiTai;

    @Override
    public Serializable pkVal() {
        return this.smDataId;
    }

}
