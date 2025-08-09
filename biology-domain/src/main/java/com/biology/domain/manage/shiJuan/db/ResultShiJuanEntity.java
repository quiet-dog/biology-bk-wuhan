package com.biology.domain.manage.shiJuan.db;

import java.io.Serializable;
import java.util.List;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.biology.common.core.base.BaseEntity;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@TableName(value = "manage_xl_shijuan_result", autoResultMap = true)
@ApiModel(value = "ReceiveEntity对象", description = "心理试卷表")
public class ResultShiJuanEntity extends BaseEntity<ResultShiJuanEntity> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "result_id", type = IdType.AUTO)
    private Long resultId;

    @TableField(value = "type")
    private String type;

    @TableField(value = "score")
    private Double score;

    @TableField(value = "xl_shi_juan_id")
    private Long xlShiJuanId;

    @TableField(value = "xl_fang_an_id")
    private Long xlFangAnId;

    @TableField(value = "last_time")
    private Long lastTime;

    @TableField(value = "result", typeHandler = JacksonTypeHandler.class)
    private List<Score> result;

    @TableField(value = "user_id")
    private Long userId;

    @TableField(value = "start_time")
    private Long startTime;

    @TableField(value = "use_time")
    private Long useTime;

    @TableField(value = "ce_ping")
    private String cePing;

    @TableField(value = "gan_yu_fang_shi")
    private String ganYuFangShi;

    @TableField(value = "gan_yu_time")
    private Long ganYuTime;

    @TableField(value = "exec_user")
    private String execUser;

    @TableField(value = "gan_yu_result")
    private String ganYuResult;

    @Override
    public Serializable pkVal() {
        return this.resultId;
    }
}
