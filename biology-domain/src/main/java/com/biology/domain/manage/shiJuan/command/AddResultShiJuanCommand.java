package com.biology.domain.manage.shiJuan.command;

import java.util.List;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.biology.domain.manage.shiJuan.db.Score;

import lombok.Data;

@Data
public class AddResultShiJuanCommand {

    private String type;

    private Double score;

    private Long xlShiJuanId;

    private Long xlFangAnId;

    private List<Score> result;

    private Long lastTime;

    private Long userId;

    private Long startTime;

    private Long useTime;

    private String cePing;

    private String ganYuFangShi;

    private Long ganYuTime;

    private String execUser;

    private String ganYuResult;
}
